package com.backend.app.repos;

import com.backend.app.domain.Plan;
import com.backend.app.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class PlanListener extends AbstractMongoEventListener<Plan> {

    private final PrimarySequenceService primarySequenceService;

    public PlanListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Plan> event) {
        if (event.getSource().getPlanId() == null) {
            event.getSource().setPlanId(((int)primarySequenceService.getNextValue()));
        }
    }

}
