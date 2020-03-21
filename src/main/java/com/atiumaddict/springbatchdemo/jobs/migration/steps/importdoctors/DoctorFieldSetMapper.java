package com.atiumaddict.springbatchdemo.jobs.migration.steps.importdoctors;

import com.atiumaddict.springbatchdemo.dto.DoctorDto;
import com.atiumaddict.springbatchdemo.dto.Gender;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class DoctorFieldSetMapper implements FieldSetMapper<DoctorDto> {
    @Override
    public DoctorDto mapFieldSet(FieldSet fieldSet) {
        DoctorDto dto = new DoctorDto();

        dto.setId(fieldSet.readLong("id"));
        dto.setFirstName(fieldSet.readString("firstName"));
        dto.setLastName(fieldSet.readString("lastName"));
        dto.setGender(Gender.valueOf(fieldSet.readString("gender")).toString());

        return dto;
    }

}