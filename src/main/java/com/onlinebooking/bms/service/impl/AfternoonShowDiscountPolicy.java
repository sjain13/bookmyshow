package com.onlinebooking.bms.service.impl;

import java.time.LocalTime;

import com.onlinebooking.bms.model.ShowEntity;
import com.onlinebooking.bms.model.TicketEntity;

import com.onlinebooking.bms.service.DiscountPolicy;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AfternoonShowDiscountPolicy implements DiscountPolicy {
    @Override
    public double applyDiscount(double amount, int seatCount, ShowEntity show, TicketEntity ticket) {
        LocalTime showTime = show.getShowTime();
        boolean isAfternoon = showTime.isAfter(LocalTime.of(12, 0)) && showTime.isBefore(LocalTime.of(16, 0));
        if (isAfternoon) {
            System.out.println("Afternoon show discount applied");
            return amount * 0.8; // 20% discount for afternoon shows
        }
        return amount;
    }
}
