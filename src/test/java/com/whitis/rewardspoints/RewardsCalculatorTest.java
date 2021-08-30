package com.whitis.rewardspoints;

import com.whitis.rewardspoints.service.RewardsCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RewardsCalculatorTest {

    private RewardsCalculator rewardsCalculator;

    @BeforeEach
    void init() {
        this.rewardsCalculator = new RewardsCalculator();
    }

    @Test
    public void givenANumberUnder50WhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(25));
        assertEquals(0, actual);
    }

    @Test
    public void givenANumberBetween50And100WhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(75));
        assertEquals(25L, actual);
    }

    @Test
    public void givenPurchaseAmountOf50WhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(50));
        assertEquals(0, actual);
    }

    @Test
    public void givenPurchaseAmountOf50PlusLowChangeWhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(50.25));
        assertEquals(0, actual);
    }

    @Test
    public void givenPurchaseAmountOf50PlusChangeWhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(50.50));
        assertEquals(0, actual);
    }

    @Test
    public void givenPurchaseAmountOf50PlusHigChangeWhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(50.75));
        assertEquals(0, actual);
    }

    @Test
    public void givenPurchaseAmountOver100WhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(120));
        assertEquals(90, actual);
    }

    @Test
    public void givenPurchaseAmountOf100PlusLowChangeWhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(100.25));
        assertEquals(50, actual);
    }

    @Test
    public void givenPurchaseAmountOf100PlusChangeWhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(100.50));
        assertEquals(50, actual);
    }

    @Test
    public void givenPurchaseAmountOf100PlusHigChangeWhenCalculateRewardsThenReturnsCorrectly() {
        var actual = rewardsCalculator.calculateRewards(BigDecimal.valueOf(100.75));
        assertEquals(50, actual);
    }
}