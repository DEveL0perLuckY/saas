package com.backend.app.rest;

import com.backend.app.model.SubscriptionDTO;
import com.backend.app.service.SubscriptionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionResource {

    private final SubscriptionService subscriptionService;

    public SubscriptionResource(final SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.findAll());
    }


    @GetMapping("/{userID}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionByuser(
            @PathVariable(name = "userID") final Integer userID) {
        return ResponseEntity.ok(subscriptionService.getUserSub(userID));
    }
   
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSubscription(
            @RequestBody @Valid final SubscriptionDTO subscriptionDTO) {
        final Integer createdSubscriptionId = subscriptionService.create(subscriptionDTO);
        return new ResponseEntity<>(createdSubscriptionId, HttpStatus.CREATED);
    }
    
    @PutMapping("/user/{userId}")
    public ResponseEntity<Void> updateUserSubscription(
            @PathVariable(name = "userId") final Integer userId,
            @RequestBody @Valid final SubscriptionDTO subscriptionDTO) {
        subscriptionService.updateByUser(userId, subscriptionDTO);
        return ResponseEntity.ok().build();
    }
    

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSubscriptionsByUser(
        @PathVariable(name = "userId") final Integer userId) {
        subscriptionService.deleteByUser(userId);
        return ResponseEntity.noContent().build();
    }

}
