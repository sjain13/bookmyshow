/**
 * 
 */
package com.onlinebooking.bms.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebooking.bms.dto.PageResponse;
import com.onlinebooking.bms.dto.ShowDto;
import com.onlinebooking.bms.service.ShowService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping("show")
@Validated
public class ShowController {

	@Autowired
	private ShowService showService;

	@GetMapping("/search/{pageNo}/{limit}")
	public ResponseEntity<PageResponse<ShowDto>> search(
			@PathVariable(name = "pageNo") @Min(value = 1, message = "Page No Cannot be less than 1") int pageNo,
			@PathVariable(name = "limit") @Min(value = 1, message = "Limit Cannot be less than 1") int limit,
			@RequestParam(name = "movieName", required = false) String movieName,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "showDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate showDate,
			@RequestParam(name = "showTime", required = false) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime showTime) {

		log.info("Received Request to search shows for Page: [Number: " + pageNo + ", Limit: " + limit + "]");

		return ResponseEntity.ok(showService.searchShows(movieName, city, showDate, showTime, pageNo, limit));
	}

	@PostMapping("add")
	public ResponseEntity<ShowDto> addShow(@RequestBody ShowDto showDto) {

		log.info("Received Request to add new show: " + showDto);

		return ResponseEntity.ok(showDto);
	}

}