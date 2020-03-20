package com.atiumaddict.springbatchdemo.jobs.migration.steps.importviruses;

import com.atiumaddict.springbatchdemo.dto.VirusDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class VirusFieldSetMapper implements FieldSetMapper<VirusDto> {
    @Override
    public VirusDto mapFieldSet(FieldSet fieldSet) {
        VirusDto dto = new VirusDto();

        dto.setName(fieldSet.readString("name"));
        dto.setDescription(fieldSet.readString("description"));

        return dto;
    }

}