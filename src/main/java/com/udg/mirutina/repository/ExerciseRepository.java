package com.udg.mirutina.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udg.mirutina.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Serializable> {

}
