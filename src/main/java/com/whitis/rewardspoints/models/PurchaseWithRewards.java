package com.whitis.rewardspoints.models;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PurchaseWithRewards {
    Purchase purchase;
    long rewards;
}
