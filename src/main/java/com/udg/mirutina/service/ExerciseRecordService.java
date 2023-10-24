package com.udg.mirutina.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udg.mirutina.model.ExerciseRecord;
import com.udg.mirutina.repository.ExerciseRecordRepository;


@Service
public class ExerciseRecordService {

	@Autowired
	private ExerciseRecordRepository repository;
	
	public List<ExerciseRecord> findAll() {
		return this.repository.findAll();
	}

	public Optional<ExerciseRecord> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public ExerciseRecord findByPassengerId(Long id) {
        return this.repository.findByPassengerId(id);
    }
	
	public List<ExerciseRecord> findAllWhoCompletedChallengeToday() {
		
        LocalDate date = LocalDate.now();
        Date today = java.sql.Date.valueOf(date); 

        return repository.findByChallengeCompletedAndCreatedAt(true, today);
    }
	
	public boolean hasCompletedChallenge(Long passengerId) {
		
        LocalDate date = LocalDate.now();
        Date today = java.sql.Date.valueOf(date); 

        List<ExerciseRecord> todaysRecords = repository.findByPassengerIdAndCreatedAt(passengerId, today);

        for (ExerciseRecord record : todaysRecords) {
            if (record.isChallengeCompleted()) {
                return true; // Reto completado si algún registro tiene el reto como completado
            }
        }
        return false; // Reto no completado si ningún registro del día cumple el criterio
    }
	
	@Transactional
	public ExerciseRecord save(ExerciseRecord exerciseRecord) {
		return this.repository.save(exerciseRecord);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

}
