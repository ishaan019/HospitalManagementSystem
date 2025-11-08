package com.ss.patient.service.services;

import com.ss.patient.service.entities.Patient;
import com.ss.patient.service.repositories.PatientRepository;
import com.ss.patient.service.controllers.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    // Create patient
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Get patient by ID
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

//    public Optional<Patient> getActivePatient(Long patientId) {
//        return patientRepository.findByIdAndStatus(id, "ACTIVE");
//    }

    // Update patient
    public Patient updatePatient(Long id, Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setName(updatedPatient.getName());
                    patient.setEmail(updatedPatient.getEmail());
                    patient.setPhone(updatedPatient.getPhone());
                    patient.setDob(updatedPatient.getDob());
                    return patientRepository.save(patient);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    }

    // Delete patient
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id " + id);
        }
        patientRepository.deleteById(id);
    }

    // Get patients with pagination and optional filtering by name or phone
    public Page<Patient> getPatientsWithFilter(String name, String phone, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return patientRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        if (phone != null && !phone.isEmpty()) {
            return patientRepository.findByPhoneContaining(phone, pageable);
        }
        return patientRepository.findAll(pageable);
    }
}