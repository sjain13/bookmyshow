/**
 * 
 */
package com.onlinebooking.bms.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlinebooking.bms.enums.TheaterType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
@Table(name = "theaters")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TheaterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private TheaterType type;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "address", nullable = false)
	private String address;

	@OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
	@JsonIgnore
	@Builder.Default
	private List<ShowEntity> shows = new ArrayList<>();

	@OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
	@JsonIgnore
	@Builder.Default
	private List<TheaterSeatsEntity> seats = new ArrayList<>();
}