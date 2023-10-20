package com.udg.mi.rutina.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercise_record")
public class ExerciseRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "passenger_id", nullable = false)
	private Passenger passenger;

	@ManyToOne(optional = false)
	@JoinColumn(name = "exercise_id", nullable = false)
	private Exercise exercise;

	private String station;

	private Integer duration;

	@Column(name = "challenge_completed")
	private boolean challengeCompleted;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

}
