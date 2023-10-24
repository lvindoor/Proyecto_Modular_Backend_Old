package com.udg.mirutina.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udg.mirutina.model.Exercise;
import com.udg.mirutina.model.ExerciseRecord;
import com.udg.mirutina.model.Passenger;
import com.udg.mirutina.pojo.Response;
import com.udg.mirutina.service.ExerciseRecordService;
import com.udg.mirutina.service.ExerciseService;
import com.udg.mirutina.service.PassengerService;
import com.udg.mirutina.service.ResponseSerivce;

@RestController
@RequestMapping("/api/exercise-record")
public class ExerciseRecordController {
	
	@Autowired
	private ExerciseRecordService exerciseRecordServices;

	@Autowired
	private PassengerService passengerService;

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
  private ResponseSerivce resService;

	@GetMapping
	public ResponseEntity<Response> getAll() {
		try {
			Object response = exerciseRecordServices.findAll();
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getById(@PathVariable Long id) {
		try {
			Object response = exerciseRecordServices.findById(id);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/passenger/{passengerId}")
	public ResponseEntity<Response> findByPassengerId(@PathVariable Long passengerId) {
		try {
			ExerciseRecord response = exerciseRecordServices.findByPassengerId(passengerId);

			if( response == null ) {
				return new ResponseEntity<Response>(new Response(false, "Error ", null), HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<Response>(new Response(true, "Success", Arrays.asList(response)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/completed-challenge")
    public ResponseEntity<List<ExerciseRecord>> getAllWhoCompletedChallengeToday() {
        List<ExerciseRecord> records = exerciseRecordServices.findAllWhoCompletedChallengeToday();
        return ResponseEntity.ok(records);
    }

	@GetMapping("/challenge-completed/{passengerId}")
	public boolean hasPassengerCompletedChallengeToday(@PathVariable Long passengerId) {
		return exerciseRecordServices.hasCompletedChallenge(passengerId);
	}
	
	@PostMapping("/new/{passengerId}/{exerciseId}")
	public ResponseEntity<Response> create(@PathVariable Long passengerId, @PathVariable Long exerciseId,@RequestBody ExerciseRecord exerciseRecord) {
		try {

			Optional<Passenger> passenger = this.passengerService.findById(passengerId);
			if( !passenger.isPresent() ) {
        String message = Passenger.class.getSimpleName() + " with id: " + passengerId + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
			}

			Optional<Exercise> exercise = this.exerciseService.findById(exerciseId);
			if( !exercise.isPresent() ) {
        String message = Exercise.class.getSimpleName() + " with id: " + exerciseId + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
			}

			exerciseRecord.setPassenger(passenger.get());
			exerciseRecord.setExercise(exercise.get());

			Object response = exerciseRecordServices.save(exerciseRecord);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<Response> update(@RequestBody ExerciseRecord exerciseRecord) 
	{
		try {
			Object response = exerciseRecordServices.save(exerciseRecord);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id) {
		try {							
			exerciseRecordServices.deleteById(id);			
			return new ResponseEntity<Response>(new Response(true, "Success", null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
