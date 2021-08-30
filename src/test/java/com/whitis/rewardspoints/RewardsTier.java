package com.whitis.rewardspoints;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class RewardsTier {
    BigDecimal start;
    BigDecimal end;
    Long multiplier;
}
