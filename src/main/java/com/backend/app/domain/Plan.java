package com.backend.app.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Document
@Getter
@Setter
public class Plan {

    @Id
    private Integer planId;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Field(
            targetType = FieldType.DECIMAL128
    )
    private BigDecimal price;

    private String features;

    @NotNull
    private Integer durationDays;

    private LocalDateTime createdAt;

    @DocumentReference(lazy = true, lookup = "{ 'plan' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Subscription> planSubscriptions;

}
