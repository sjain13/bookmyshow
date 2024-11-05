package com.onlinebooking.bms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebooking.bms.model.ShowEntity;
import com.onlinebooking.bms.model.TicketEntity;

import com.onlinebooking.bms.service.DiscountPolicy;

@Service
public class DiscountService {

    private final List<DiscountPolicy> discountPolicies;

    @Autowired
    public DiscountService(List<DiscountPolicy> discountPolicies) {
        this.discountPolicies = discountPolicies;
    }

    public double applyDiscounts(double baseAmount, int seatCount, ShowEntity show, TicketEntity ticket) {
        double discountedAmount = baseAmount;
        for (DiscountPolicy policy : discountPolicies) {
            discountedAmount = policy.applyDiscount(discountedAmount, seatCount, show, ticket);
        }
        return discountedAmount;
    }
}
