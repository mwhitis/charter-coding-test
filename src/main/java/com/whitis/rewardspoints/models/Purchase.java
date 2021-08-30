package com.whitis.rewardspoints.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@AllArgsConstructor
public class Purchase {
    String customerId;
    Instant purchaseDateTime;
    BigDecimal purchaseAmount;
}
