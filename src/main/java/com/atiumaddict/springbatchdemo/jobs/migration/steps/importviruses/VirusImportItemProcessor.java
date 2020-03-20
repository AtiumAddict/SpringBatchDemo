package com.atiumaddict.springbatchdemo.jobs.migration.steps.importviruses;

import com.atiumaddict.springbatchdemo.configuration.BatchConfiguration;
import com.atiumaddict.springbatchdemo.dto.InfectionDto;
import com.atiumaddict.springbatchdemo.dto.InfectionType;
import com.atiumaddict.springbatchdemo.dto.VirusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class VirusImportItemProcessor implements ItemProcessor<VirusDto, InfectionDto> {

    private BatchConfiguration batch;

    public VirusImportItemProcessor(BatchConfiguration batch) {
        this.batch = batch;
    }

    @Override
    public InfectionDto process(VirusDto virusDto) throws Exception {
        InfectionDto infectionDto = new InfectionDto();
        infectionDto.setId(batch.jdbcTemplate.queryForObject("SELECT NEXT VALUE FOR infection_id_seq", Long.class));
        infectionDto.setName(virusDto.getName());
        infectionDto.setDescription(virusDto.getDescription());
        infectionDto.setType(InfectionType.VIRAL.toString());

        return infectionDto;
    }
}
