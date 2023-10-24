package com.udg.mirutina.controller;

import java.util.Arrays;
import java.util.List;

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
import com.udg.mirutina.model.Exercise.ExerciseType;
import com.udg.mirutina.pojo.Response;
import com.udg.mirutina.service.ExerciseService;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {
	
	@Autowired
	private ExerciseService exerciseServices;
	
	@GetMapping
	public ResponseEntity<Response> getAll() {
		try {
			Object response = exerciseServices.findAll();
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getById(@PathVariable Long id) {
		try {
			Object response = exerciseServices.findById(id);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/types")
    public List<ExerciseType> getAllExerciseTypes() {
        return Arrays.asList(ExerciseType.values());
    }
	
	@PostMapping
	public ResponseEntity<Response> create(@RequestBody Exercise exercise) {
		try {
			Object response = exerciseServices.save(exercise);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<Response> update(@RequestBody Exercise exercise) 
	{
		try {
			Object response = exerciseServices.save(exercise);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id) {
		try {							
			exerciseServices.deleteById(id);			
			return new ResponseEntity<Response>(new Response(true, "Success", null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
