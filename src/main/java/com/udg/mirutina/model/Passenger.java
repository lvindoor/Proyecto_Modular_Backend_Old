package com.udg.mirutina.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passenger")
public class Passenger {

	public enum PassengerType {
		CHILDREN(1), // Niños
		STUDENTS(2), // Estudiantes
		TEACHERS(3), // Maestros
		DISABLED(4), // Discapacitados
		ELDERLY(5); // Adultos mayores
		
		private final int value;

		PassengerType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "passenger_type")
	private PassengerType passengerType;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;
}
