package com.onlinebooking.bms.service;

import com.onlinebooking.bms.model.ShowEntity;
import com.onlinebooking.bms.model.TicketEntity;

public interface DiscountPolicy {
    double applyDiscount(double amount, int seatCount, ShowEntity show, TicketEntity ticket);
}
