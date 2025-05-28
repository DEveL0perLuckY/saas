package com.backend.app.repos;

import com.backend.app.domain.Subscription;
import com.backend.app.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class SubscriptionListener extends AbstractMongoEventListener<Subscription> {

    private final PrimarySequenceService primarySequenceService;

    public SubscriptionListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Subscription> event) {
        if (event.getSource().getSubscriptionId() == null) {
            event.getSource().setSubscriptionId(((int)primarySequenceService.getNextValue()));
        }
    }

}
