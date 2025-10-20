package org.vitacare.mapper;

import org.vitacare.dto.appointment.AppointmentDTO;
import org.vitacare.entity.Appointment;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Speciality;

public class AppointmentMapper {


    public static AppointmentDTO toDTO(Appointment appointment) {
        if (appointment == null) return null;

        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setStartAt(appointment.getStartAt());
        dto.setEndAt(appointment.getEndAt());
        dto.setStatus(appointment.getStatus());
        dto.setType(appointment.getType());
        dto.setCreatedBy(appointment.getCreatedBy());


        Patient patient = appointment.getPatient();
        if (patient != null) {
            dto.setPatientId(patient.getId());
            dto.setPatientFirstName(patient.getFirstName());
            dto.setPatientLastName(patient.getLastName());
        }


        Doctor doctor = appointment.getDoctor();
        if (doctor != null) {
            dto.setDoctorId(doctor.getId());
            dto.setDoctorFirstName(doctor.getFirstName());
            dto.setDoctorLastName(doctor.getLastName());


            Speciality speciality = doctor.getSpeciality();
            if (speciality != null) {
                dto.setDoctorSpeciality(speciality.getName());


                if (speciality.getDepartment() != null) {
                    dto.setDoctorDepartment(speciality.getDepartment().getName());
                }
            }
        }

        return dto;
    }


    public static Appointment toEntity(AppointmentDTO dto, Doctor doctor, Patient patient) {
        if (dto == null) return null;

        Appointment appointment = new Appointment();
        if (dto.getId() != null) appointment.setId(dto.getId());
        appointment.setStartAt(dto.getStartAt());
        appointment.setEndAt(dto.getEndAt());
        appointment.setStatus(dto.getStatus());
        appointment.setType(dto.getType());
        appointment.setCreatedBy(dto.getCreatedBy());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        return appointment;
    }
}
