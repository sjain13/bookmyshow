/**
 * 
 */
package com.onlinebooking.bms.service;

import com.onlinebooking.bms.dto.BookTicketRequestDto;
import com.onlinebooking.bms.dto.TicketDto;


public interface TicketService {

	TicketDto bookTicket(BookTicketRequestDto bookTicketRequestDto);

	TicketDto getTicket(long id);
}