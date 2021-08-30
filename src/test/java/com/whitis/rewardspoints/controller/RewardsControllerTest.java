package com.whitis.rewardspoints.controller;

import com.whitis.rewardspoints.service.RewardsSummaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.whitis.rewardspoints.models.Purchase;
import com.whitis.rewardspoints.models.RewardsSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardsController.class)
class RewardsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsSummaryService rewardsSummaryService;

    @BeforeEach
    void setup() {

        Map<String, Long> months = new HashMap<>();
        months.put("2021-08", 50L);

        RewardsSummary rewardsSummary = RewardsSummary.builder()
                .totalRewards(50L)
                .months(months)
           .build();
        when(this.rewardsSummaryService.rewardsSummary(any())).thenReturn(asList(rewardsSummary));
  }

    @Test
    void givenANullPostControllerThrows() throws Exception {
        mockMvc.perform(post("/rewards"))
                .andExpect(status().is(415));
    }

    @Test
    void givenAGoodPostBodyWhenPostToRewardsSummaryThenReturns() throws Exception {
        Purchase purchase = Purchase.builder()
                .purchaseDateTime(Instant.parse("2021-08-08T15:32:09Z"))
                .customerId("1")
                .purchaseAmount(BigDecimal.valueOf(100.23))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(asList(purchase) );

        mockMvc.perform(
                    post("/rewards")
                            .contentType(APPLICATION_JSON)
                            .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRewards").value(50L))
                .andExpect(jsonPath("$.months").isNotEmpty())
                .andExpect(jsonPath("$.months").isMap())
                .andExpect(jsonPath("$.months.size()").value(1))
                .andExpect(jsonPath("$.months.2021-08").value(50L));
    }
}
