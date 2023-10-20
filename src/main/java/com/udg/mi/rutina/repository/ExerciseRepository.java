package com.udg.mi.rutina.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udg.mi.rutina.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Serializable> {

}
