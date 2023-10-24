package com.udg.mirutina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udg.mirutina.model.Exercise;
import com.udg.mirutina.repository.ExerciseRepository;

@Service
public class ExerciseService {

  @Autowired
  private ExerciseRepository repository;

  public List<Exercise> findAll() {
    return this.repository.findAll();
  }

  public Optional<Exercise> findById(Long id) {
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
