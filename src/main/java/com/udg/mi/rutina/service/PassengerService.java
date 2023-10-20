package com.udg.mi.rutina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udg.mi.rutina.model.Passenger;
import com.udg.mi.rutina.model.Passenger.PassengerType;
import com.udg.mi.rutina.repository.PassengerRepository;

@Service
public class PassengerService {

	@Autowired
	private PassengerRepository repository;
	
	public List<Passenger> findAll() {
		return this.repository.findAll();
	}

	public Optional<Passenger> findUserById(Long id) {
        return repository.findById(id);
    }
	
	public boolean isPassengerEligibleForExercise(Long passengerId) {
		
        Optional<Passenger> optionalPassenger = repository.findById(passengerId);
        if(optionalPassenger.isPresent()) {

            Passenger passenger = optionalPassenger.get();
            PassengerType passengerType = passenger.getPassengerType();
            
            // Verificar si el pasajero es apto para hacer ejercicio
            return passengerType != PassengerType.DISABLED && passengerType != PassengerType.ELDERLY;
        } else {
            return false;
        }
    }
	
	@Transactional
	public Passenger save(Passenger passenger) {
		return this.repository.save(passenger);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

}
