package com.onlinebooking.bms.service.impl;

import com.onlinebooking.bms.model.ShowEntity;
import com.onlinebooking.bms.model.TicketEntity;

import com.onlinebooking.bms.service.DiscountPolicy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;



@Log4j2
@Service
public class ThirdTicketDiscountPolicy implements DiscountPolicy {
    @Override
    public double applyDiscount(double amount, int seatCount, ShowEntity show, TicketEntity ticket) {
        System.out.println("ThirdTicketDiscountPolicy:"+ "Amount:" + amount + " SeatCount:" + seatCount);
        if (seatCount == 3) {
            System.out.println("Apply discount on 3rd Ticket");
            return amount * 0.5; // 50% discount on the third ticket
        }
        return amount;
    }
}
