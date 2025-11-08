package com.ss.doctorandscheduling.service.services;

import com.ss.doctorandscheduling.service.entities.Doctor;
import com.ss.doctorandscheduling.service.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Page<Doctor> getAllDoctors(String department, String name, Pageable pageable) {
        if (department != null && !department.isBlank()) {
            return doctorRepository.findByDepartmentContainingIgnoreCase(department, pageable);
        } else if (name != null && !name.isBlank()) {
            return doctorRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            return doctorRepository.findAll(pageable);
        }
    }

    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + doctorId));
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long doctorId, Doctor updatedDoctor) {
        Doctor existingDoctor = getDoctorById(doctorId);
        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setEmail(updatedDoctor.getEmail());
        existingDoctor.setPhone(updatedDoctor.getPhone());
        existingDoctor.setDepartment(updatedDoctor.getDepartment());
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        return doctorRepository.save(existingDoctor);
    }

    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }
}

