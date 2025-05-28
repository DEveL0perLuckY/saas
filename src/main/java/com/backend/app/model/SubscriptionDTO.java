package com.backend.app.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SubscriptionDTO {

    private Integer subscriptionId;

    @NotNull
    private Status status;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
    private Integer user;

    @NotNull
    private Integer plan;

}
