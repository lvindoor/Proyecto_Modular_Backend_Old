package com.udg.mi.rutina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udg.mi.rutina.model.Exercise;
import com.udg.mi.rutina.repository.ExerciseRepository;

@Service
public class ExerciseService {

	@Autowired
	private ExerciseRepository repository;
	
	public List<Exercise> findAll() {
		return this.repository.findAll();
	}

	public Optional<Exercise> findUserById(Long id) {
        return repository.findById(id);
    }
	
	@Transactional
	public Exercise save(Exercise exercise) {
		return this.repository.save(exercise);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

}
