package com.backend.app.service;

import com.backend.app.domain.Plan;
import com.backend.app.domain.Subscription;
import com.backend.app.model.PlanDTO;
import com.backend.app.repos.PlanRepository;
import com.backend.app.repos.SubscriptionRepository;
import com.backend.app.util.NotFoundException;
import com.backend.app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PlanService(final PlanRepository planRepository,
            final SubscriptionRepository subscriptionRepository) {
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<PlanDTO> findAll() {
        final List<Plan> plans = planRepository.findAll(Sort.by("planId"));
        return plans.stream()
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .toList();
    }

    public PlanDTO get(final Integer planId) {
        return planRepository.findById(planId)
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PlanDTO planDTO) {
        final Plan plan = new Plan();
        mapToEntity(planDTO, plan);
        return planRepository.save(plan).getPlanId();
    }

    public void update(final Integer planId, final PlanDTO planDTO) {
        final Plan plan = planRepository.findById(planId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planDTO, plan);
        planRepository.save(plan);
    }

    public void delete(final Integer planId) {
        planRepository.deleteById(planId);
    }

    private PlanDTO mapToDTO(final Plan plan, final PlanDTO planDTO) {
        planDTO.setPlanId(plan.getPlanId());
        planDTO.setName(plan.getName());
        planDTO.setPrice(plan.getPrice());
        planDTO.setFeatures(plan.getFeatures());
        planDTO.setDurationDays(plan.getDurationDays());
        planDTO.setCreatedAt(plan.getCreatedAt());
        return planDTO;
    }

    private Plan mapToEntity(final PlanDTO planDTO, final Plan plan) {
        plan.setName(planDTO.getName());
        plan.setPrice(planDTO.getPrice());
        plan.setFeatures(planDTO.getFeatures());
        plan.setDurationDays(planDTO.getDurationDays());
        plan.setCreatedAt(planDTO.getCreatedAt());
        return plan;
    }

    public ReferencedWarning getReferencedWarning(final Integer planId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Plan plan = planRepository.findById(planId)
                .orElseThrow(NotFoundException::new);
        final Subscription planSubscription = subscriptionRepository.findFirstByPlan(plan);
        if (planSubscription != null) {
            referencedWarning.setKey("plan.subscription.plan.referenced");
            referencedWarning.addParam(planSubscription.getSubscriptionId());
            return referencedWarning;
        }
        return null;
    }

}
