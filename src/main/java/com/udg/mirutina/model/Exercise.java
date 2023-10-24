package com.udg.mirutina.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercise")
public class Exercise {

	public enum ExerciseType {
	    SPINNING(1), // Bicicleta
	    SQUATS(2); // Sentadilla
		
		private final int value;

		ExerciseType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_type", unique = true)
    private ExerciseType exerciseType;
}

