package com.udg.mi.rutina.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udg.mi.rutina.model.ExerciseRecord;
import com.udg.mi.rutina.pojo.Response;
import com.udg.mi.rutina.service.ExerciseRecordService;

@CrossOrigin
@RestController
@RequestMapping("/api/exercise-record")
public class ExerciseRecordController {
	
	@Autowired
	private ExerciseRecordService exerciseRecordServices;
	
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
			Object response = exerciseRecordServices.findUserById(id);
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
	
	@PostMapping
	public ResponseEntity<Response> create(@RequestBody ExerciseRecord exerciseRecord) {
		try {
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
