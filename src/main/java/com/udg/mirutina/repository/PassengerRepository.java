package com.udg.mirutina.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udg.mirutina.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Serializable> {

}
