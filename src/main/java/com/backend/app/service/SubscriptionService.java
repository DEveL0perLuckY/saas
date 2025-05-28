package com.backend.app.service;

import com.backend.app.domain.Plan;
import com.backend.app.domain.Subscription;
import com.backend.app.domain.User;
import com.backend.app.model.SubscriptionDTO;
import com.backend.app.repos.PlanRepository;
import com.backend.app.repos.SubscriptionRepository;
import com.backend.app.repos.UserRepository;
import com.backend.app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public SubscriptionService(final SubscriptionRepository subscriptionRepository,
            final UserRepository userRepository, final PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    public List<SubscriptionDTO> findAll() {
        final List<Subscription> subscriptions = subscriptionRepository.findAll(Sort.by("subscriptionId"));
        return subscriptions.stream()
                .map(subscription -> mapToDTO(subscription, new SubscriptionDTO()))
                .toList();
    }

    public void updateByUser(final Integer userId, final SubscriptionDTO subscriptionDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    
        Subscription subscription = subscriptionRepository.findFirstByUser(user);
        if (subscription == null) {
            throw new NotFoundException("Subscription not found for user");
        }
    
        mapToEntity(subscriptionDTO, subscription);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
    }
    

    public List<SubscriptionDTO> getUserSub(final Integer userId) {
        User user = userRepository.findById(userId)
        .orElseThrow(NotFoundException::new);
        return subscriptionRepository.findAllByUser(user).stream()
                .map(subscription -> mapToDTO(subscription, new SubscriptionDTO()))
                .toList();
    }

    public Integer create(final SubscriptionDTO subscriptionDTO) {
        final Subscription subscription = new Subscription();
        mapToEntity(subscriptionDTO, subscription);
        return subscriptionRepository.save(subscription).getSubscriptionId();
    }

 

    private SubscriptionDTO mapToDTO(final Subscription subscription,
            final SubscriptionDTO subscriptionDTO) {
        subscriptionDTO.setSubscriptionId(subscription.getSubscriptionId());
        subscriptionDTO.setStatus(subscription.getStatus());
        subscriptionDTO.setStartDate(subscription.getStartDate());
        subscriptionDTO.setEndDate(subscription.getEndDate());
        subscriptionDTO.setCreatedAt(subscription.getCreatedAt());
        subscriptionDTO.setUpdatedAt(subscription.getUpdatedAt());
        subscriptionDTO.setUser(subscription.getUser() == null ? null : subscription.getUser().getUserId());
        subscriptionDTO.setPlan(subscription.getPlan() == null ? null : subscription.getPlan().getPlanId());
        return subscriptionDTO;
    }

    private Subscription mapToEntity(final SubscriptionDTO subscriptionDTO,
            final Subscription subscription) {
        subscription.setStatus(subscriptionDTO.getStatus());
        subscription.setStartDate(subscriptionDTO.getStartDate());
        subscription.setEndDate(subscriptionDTO.getEndDate());
        subscription.setCreatedAt(subscriptionDTO.getCreatedAt());
        subscription.setUpdatedAt(subscriptionDTO.getUpdatedAt());
        final User user1 = subscriptionDTO.getUser() == null ? null : userRepository.findById(subscriptionDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        subscription.setUser(user1);
        final Plan plan1 = subscriptionDTO.getPlan() == null ? null : planRepository.findById(subscriptionDTO.getPlan())
                .orElseThrow(() -> new NotFoundException("plan not found"));
        subscription.setPlan(plan1);
        return subscription;
    }


    public void deleteByUser(final Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        List<Subscription> subscriptions = subscriptionRepository.findAllByUser(user);
        subscriptionRepository.deleteAll(subscriptions);
    }
}
