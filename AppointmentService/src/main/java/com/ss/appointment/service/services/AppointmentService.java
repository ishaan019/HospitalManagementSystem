package com.ss.appointment.service.services;

import com.ss.appointment.service.entities.Appointment;
import com.ss.appointment.service.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Book appointment
    public Appointment bookAppointment(Appointment appointment) {
        checkSlotCollision(appointment.getDoctorId(), appointment.getSlotStart(), appointment.getSlotEnd());
        appointment.setStatus("BOOKED");
        return appointmentRepository.save(appointment);
    }

    // Reschedule appointment
    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newStart, LocalDateTime newEnd) {
        Appointment existing = getAppointmentById(appointmentId);
        checkSlotCollision(existing.getDoctorId(), newStart, newEnd, appointmentId);
        existing.setSlotStart(newStart);
        existing.setSlotEnd(newEnd);
        existing.setStatus("RESCHEDULED");
        return appointmentRepository.save(existing);
    }

    // Cancel appointment
    public void cancelAppointment(Long appointmentId) {
        Appointment existing = getAppointmentById(appointmentId);
        existing.setStatus("CANCELLED");
        appointmentRepository.save(existing);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
    }

    public Page<Appointment> getAppointments(Long patientId, Long doctorId, String department, Pageable pageable) {
        if (patientId != null) {
            return appointmentRepository.findByPatientId(patientId, pageable);
        } else if (doctorId != null) {
            return appointmentRepository.findByDoctorId(doctorId, pageable);
        } else if (department != null && !department.isBlank()) {
            return appointmentRepository.findByDepartmentContainingIgnoreCase(department, pageable);
        } else {
            return appointmentRepository.findAll(pageable);
        }
    }

    // Slot collision check for booking
    private void checkSlotCollision(Long doctorId, LocalDateTime start, LocalDateTime end) {
        checkSlotCollision(doctorId, start, end, null);
    }

    private void checkSlotCollision(Long doctorId, LocalDateTime start, LocalDateTime end, Long excludeAppointmentId) {
        List<Appointment> collisions = appointmentRepository.findByDoctorIdAndSlotStartLessThanAndSlotEndGreaterThanAndStatus(
                doctorId, end, start, "BOOKED");
        if (excludeAppointmentId != null) {
            collisions.removeIf(a -> a.getAppointmentId().equals(excludeAppointmentId));
        }
        if (!collisions.isEmpty()) {
            throw new RuntimeException("Slot not available for the doctor");
        }
    }
}
