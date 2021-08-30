package com.whitis.rewardspoints.controller;

import com.whitis.rewardspoints.service.RewardsSummaryService;
import lombok.RequiredArgsConstructor;
import com.whitis.rewardspoints.models.Purchase;
import com.whitis.rewardspoints.models.RewardsSummary;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RewardsController {

    private final RewardsSummaryService rewardsSummaryService;

    @PostMapping(path = "/rewards")
    public List<RewardsSummary> calculateRewardsForPurchases(@RequestBody List<Purchase> purchases) {
        Assert.notNull(purchases, "purchases cannot be null");
        return rewardsSummaryService.rewardsSummary(purchases);
    }
}
