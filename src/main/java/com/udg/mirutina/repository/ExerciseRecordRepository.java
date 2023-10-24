package com.udg.mirutina.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udg.mirutina.model.ExerciseRecord;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Serializable> {

  List<ExerciseRecord> findByPassengerIdAndCreatedAt(Long passengerId, Date createdAt);

  List<ExerciseRecord> findByChallengeCompletedAndCreatedAt(boolean challengeCompleted, Date createdAt);

  ExerciseRecord findByPassengerId(Long id);

}