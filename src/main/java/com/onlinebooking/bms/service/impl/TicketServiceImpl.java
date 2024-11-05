/**
 * 
 */
package com.onlinebooking.bms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebooking.bms.adapter.TicketAdapter;
import com.onlinebooking.bms.dto.BookTicketRequestDto;
import com.onlinebooking.bms.dto.TicketDto;
import com.onlinebooking.bms.exception.DependencyException;
import com.onlinebooking.bms.model.ShowEntity;
import com.onlinebooking.bms.model.ShowSeatsEntity;
import com.onlinebooking.bms.model.TicketEntity;
import com.onlinebooking.bms.model.UserEntity;
import com.onlinebooking.bms.repository.ShowRepository;
import com.onlinebooking.bms.repository.TicketRepository;
import com.onlinebooking.bms.repository.UserRepository;
import com.onlinebooking.bms.service.TicketService;
import com.onlinebooking.bms.service.impl.DiscountService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private DiscountService discountService;

	@Override
	public TicketDto bookTicket(BookTicketRequestDto bookTicketRequestDto) {

		log.info("Searching User by id: " + bookTicketRequestDto.getUserId());

		Optional<UserEntity> optionalUser = userRepository.findById(bookTicketRequestDto.getUserId());

		if (!optionalUser.isPresent()) {
			throw new DependencyException("User Not Found with ID: " + bookTicketRequestDto.getUserId() + " to book ticket");
		}

		log.info("Searching Show by id: " + bookTicketRequestDto.getShowId());

		Optional<ShowEntity> optionalShow = showRepository.findById(bookTicketRequestDto.getShowId());

		if (!optionalShow.isPresent()) {
			throw new DependencyException("Show Not Found with ID: " + bookTicketRequestDto.getUserId() + " to book ticket");
		}

		Set<String> requestedSeats = bookTicketRequestDto.getSeatsNumbers();

		log.info("Requested Bookings For Seats: " + requestedSeats + " of Show: " + bookTicketRequestDto.getShowId() + " by User: " + bookTicketRequestDto.getUserId());

		List<ShowSeatsEntity> showSeatsEntities = optionalShow.get().getSeats();

		log.info("Total Number of Seats in Show: " + bookTicketRequestDto.getShowId() + " are " + showSeatsEntities.size());

		showSeatsEntities =
				showSeatsEntities
						.stream()
						.filter(seat -> seat.getSeatType().equals(bookTicketRequestDto.getSeatType())
								&& !seat.isBooked()
								&& requestedSeats.contains(seat.getSeatNumber()))
						.collect(Collectors.toList());

		if (showSeatsEntities.size() != requestedSeats.size()) {
			throw new DependencyException("Seats Not Available for Booking");
		}

		log.info("Seats: " + requestedSeats + " are avaialble in Show: " + bookTicketRequestDto.getShowId() + " for booking to User " + bookTicketRequestDto.getUserId());

		TicketEntity ticketEntity =
				TicketEntity.builder()
						.user(optionalUser.get())
						.show(optionalShow.get())
						.seats(showSeatsEntities)
						.bookedAt(new Date())
						.build();

		double amount = 0.0;
		String allotedSeats = "";
		int seatCount = 0;

		for (ShowSeatsEntity seatsEntity : showSeatsEntities) {
			seatsEntity.setBooked(true);
			seatsEntity.setBookedAt(new Date());
			seatsEntity.setTicket(ticketEntity);
			double seatRate = seatsEntity.getRate();
        	seatCount++;

        	// Apply discounts to each ticket price
        	seatRate = discountService.applyDiscounts(seatRate, seatCount, optionalShow.get(), ticketEntity);

			log.info("SeatRate for " + seatCount + "tickets: " + seatRate);

			amount += seatRate;
			

			allotedSeats += seatsEntity.getSeatNumber() + " ";
		}

		ticketEntity.setAmount(amount);
		ticketEntity.setAllottedSeats(allotedSeats);

		if (CollectionUtils.isEmpty(optionalUser.get().getTicketEntities())) {
			optionalUser.get().setTicketEntities(new ArrayList<>());
		}

		optionalUser.get().getTicketEntities().add(ticketEntity);

		if (CollectionUtils.isEmpty(optionalShow.get().getTickets())) {
			optionalShow.get().setTickets(new ArrayList<>());
		}

		optionalShow.get().getTickets().add(ticketEntity);

		log.info("Creating Booking for User: " + bookTicketRequestDto.getUserId() + " in Show: " + bookTicketRequestDto.getShowId() + " for Seats: " + allotedSeats);

	
		ticketEntity = ticketRepository.save(ticketEntity);

		return TicketAdapter.toDto(ticketEntity);
	}

	@Override
	public TicketDto getTicket(long id) {
		log.info("Searching Ticket by id: " + id);

		Optional<TicketEntity> ticketEntity = ticketRepository.findById(id);

		if (!ticketEntity.isPresent()) {
			log.error("Ticket not found for id: " + id);
			throw new EntityNotFoundException("Ticket Not Found with ID: " + id);
		}

		return TicketAdapter.toDto(ticketEntity.get());
	}

}