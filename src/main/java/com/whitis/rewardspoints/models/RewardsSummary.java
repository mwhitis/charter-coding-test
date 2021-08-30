package com.whitis.rewardspoints.models;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class RewardsSummary {
    private String customerId;
    private Map<String, Long> months;
    private Long totalRewards;
}
