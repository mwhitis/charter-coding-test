package com.whitis.rewardspoints.service;

import com.whitis.rewardspoints.RewardsCalculator;
import lombok.RequiredArgsConstructor;
import com.whitis.rewardspoints.models.Purchase;
import com.whitis.rewardspoints.models.RewardsSummary;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class RewardsSummaryService {

    private final RewardsCalculator rewardsCalculator;

    public List<RewardsSummary> rewardsSummary(List<Purchase> purchases) {
        var purchaseMaps = purchases.stream().collect(groupingBy(Purchase::getCustomerId));
        List<RewardsSummary> finalSummaries = new ArrayList<>();

        for (var purch : purchaseMaps.entrySet()) {
            String customerId = purch.getKey();
            List<Purchase> customerPurchases = purch.getValue();
            Map<String, Long> pointsMap = new HashMap<>();
            Long totalPoints = 0L;
            customerPurchases.forEach(purchase -> {
                var key = getYearMonthFromInstant(purchase.getPurchaseDateTime());
                if (!pointsMap.containsKey(key)) {
                    pointsMap.put(key, 0L);
                }
                var currentVal = pointsMap.get(key);
                long purchasePoints = rewardsCalculator.calculateRewards(purchase.getPurchaseAmount());
                var newVal = currentVal + purchasePoints;
                pointsMap.put(key, newVal);
            });

            totalPoints = pointsMap.values().stream().reduce(0L, Long::sum);


            finalSummaries.add(RewardsSummary.builder()
                    .customerId(customerId)
                    .months(pointsMap)
                    .totalRewards(totalPoints)
                    .build()
            );
        }

        return finalSummaries;
    }

    private String getYearMonthFromInstant(Instant instant) {
        var date = Date.from(instant);
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        var year = calendar.get(Calendar.YEAR);
        var month = calendar.get(Calendar.MONTH);
        return String.format("%d-%02d", year, month + 1);
    }
}
