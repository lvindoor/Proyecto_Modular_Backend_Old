package com.udg.mirutina.model;


import java.sql.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercise_record")
public class ExerciseRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true) // not visible on swagger
	private Long id;

	private String station;

	private Integer duration;

	@Column(name = "challenge_completed")
	private boolean challengeCompleted;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@ManyToOne(optional = false)
	@JoinColumn(name = "passenger_id", nullable = false)
	@ApiModelProperty(hidden = true) // not visible on swagger
	private Passenger passenger;

	@ManyToOne(optional = false)
	@JoinColumn(name = "exercise_id", nullable = false)
	@ApiModelProperty(hidden = true) // not visible on swagger
	private Exercise exercise;

}
