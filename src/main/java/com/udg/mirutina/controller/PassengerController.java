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

import com.udg.mirutina.model.Passenger;
import com.udg.mirutina.model.Passenger.PassengerType;
import com.udg.mirutina.pojo.Response;
import com.udg.mirutina.service.PassengerService;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
	
	@Autowired
	private PassengerService passengerServices;
	
	@GetMapping
	public ResponseEntity<Response> getAll() {
		try {
			Object response = passengerServices.findAll();
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getById(@PathVariable Long id) {
		try {
			Object response = passengerServices.findById(id);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}/can-exercise")
    public ResponseEntity<Boolean> canPassengerExercise(@PathVariable Long id) {
        boolean canExercise = passengerServices.isPassengerEligibleForExercise(id);
        return new ResponseEntity<>(canExercise, HttpStatus.OK);
    }
	
	@GetMapping("/types")
    public List<PassengerType> getAllPassengerTypes() {
        return Arrays.asList(PassengerType.values());
    }	
	
	@PostMapping
	public ResponseEntity<Response> create(@RequestBody Passenger passenger) {
		try {
			Object response = passengerServices.save(passenger);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Passenger passenger) 
	{
		try {
			Optional<Passenger> dbPassenger = this.passengerServices.findById(id);

			if( !dbPassenger.isPresent() ) {
				String message = "Error" + " passenger with id: " +id + " not found";
				return new ResponseEntity<Response>(new Response(false, message, null), HttpStatus.INTERNAL_SERVER_ERROR);
			}

			Object response = passengerServices.save(passenger);
			return new ResponseEntity<Response>(new Response(true, "Success", response), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id) {
		try {							
			passengerServices.deleteById(id);			
			return new ResponseEntity<Response>(new Response(true, "Success", null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response(false, "Error " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
