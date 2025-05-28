package com.backend.app.rest;

import com.backend.app.model.PlanDTO;
import com.backend.app.service.PlanService;
import com.backend.app.util.ReferencedException;
import com.backend.app.util.ReferencedWarning;
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
@RequestMapping(value = "/api/plans", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanResource {

    private final PlanService planService;

    public PlanResource(final PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<List<PlanDTO>> getAllPlans() {
        return ResponseEntity.ok(planService.findAll());
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanDTO> getPlan(@PathVariable(name = "planId") final Integer planId) {
        return ResponseEntity.ok(planService.get(planId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPlan(@RequestBody @Valid final PlanDTO planDTO) {
        final Integer createdPlanId = planService.create(planDTO);
        return new ResponseEntity<>(createdPlanId, HttpStatus.CREATED);
    }

    @PutMapping("/{planId}")
    public ResponseEntity<Integer> updatePlan(@PathVariable(name = "planId") final Integer planId,
            @RequestBody @Valid final PlanDTO planDTO) {
        planService.update(planId, planDTO);
        return ResponseEntity.ok(planId);
    }

    @DeleteMapping("/{planId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlan(@PathVariable(name = "planId") final Integer planId) {
        final ReferencedWarning referencedWarning = planService.getReferencedWarning(planId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        planService.delete(planId);
        return ResponseEntity.noContent().build();
    }

}
