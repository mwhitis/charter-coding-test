package com.whitis.rewardspoints.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Component
public class RewardsCalculator {

    public static final int DOUBLE_REWARD_MULTIPLIER = 2;
    public static final long DEFAULT_REWARD_AMOUNT = 0L;
    public static final BigDecimal DOUBLE_REWARD_FLOOR = BigDecimal.valueOf(100);
    public static final BigDecimal SINGLE_REWARD_FLOOR = BigDecimal.valueOf(50);
    public static final int BIG_DECIMAL_COMPARE_SUCCESS = 0;

    public Long calculateRewards(BigDecimal purchaseAmount){
        if (isADoubleRewardPurchase(purchaseAmount)) {
            Long doubleAmount = purchaseAmount.subtract(DOUBLE_REWARD_FLOOR).longValue();
            Long doubleRewards = doubleAmount * DOUBLE_REWARD_MULTIPLIER;
            Long singleRewards = DOUBLE_REWARD_FLOOR.subtract(SINGLE_REWARD_FLOOR).longValue();
            return doubleRewards +  singleRewards;
        }

        if (isASingleRewardPurchase(purchaseAmount)) {
            return purchaseAmount.subtract(SINGLE_REWARD_FLOOR).longValue();
        }

        return DEFAULT_REWARD_AMOUNT;
    }

    private boolean isASingleRewardPurchase(BigDecimal purchaseAmount) {
        return purchaseAmount.compareTo(SINGLE_REWARD_FLOOR) > BIG_DECIMAL_COMPARE_SUCCESS
                && purchaseAmount.compareTo(DOUBLE_REWARD_FLOOR)< BIG_DECIMAL_COMPARE_SUCCESS;
    }

    private boolean isADoubleRewardPurchase(BigDecimal purchaseAmount) {
        return purchaseAmount.compareTo(DOUBLE_REWARD_FLOOR) > BIG_DECIMAL_COMPARE_SUCCESS;
    }
}