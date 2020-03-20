package com.atiumaddict.springbatchdemo.jobs.migration.steps.importviruses;

import com.atiumaddict.springbatchdemo.configuration.BatchConfiguration;
import com.atiumaddict.springbatchdemo.dto.VirusDto;
import com.atiumaddict.springbatchdemo.factory.StepFactory;
import com.atiumaddict.springbatchdemo.factory.StepGeneratorContext;
import com.atiumaddict.springbatchdemo.factory.read.FileReaderContext;
import com.atiumaddict.springbatchdemo.factory.stepgenerators.StepType;
import com.atiumaddict.springbatchdemo.utils.JobUtils;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import static com.atiumaddict.springbatchdemo.utils.JobConstants.VIRUS_IMPORT_STEP_ID;
import static com.atiumaddict.springbatchdemo.utils.JobUtils.BASEPATH_DIR;

public class VirusImportImportItemHelper {

    private static final String WRITER_SQL_STATEMENT =
            "INSERT INTO infections (" +
                    "id, name, type, description" +
                    ") \n" +
                    "VALUES (" +
                    ":id, :name, :type, :description" +
                    ")";

    @Bean
    @Qualifier(VIRUS_IMPORT_STEP_ID)
    public Step importViruses(BatchConfiguration batch) {
        FileReaderContext fileReaderContext = new FileReaderContext().builder()
                .defaultFileResource(JobUtils.getLatestResourceByFilePattern(BASEPATH_DIR, "*Viruses*"))
                .delimiter("|")
                .fieldNames(new String[]{
                        "name", "description"
                })
                .fieldSetMapper(new VirusFieldSetMapper())
                .build();
        StepGeneratorContext stepGeneratorContext = StepGeneratorContext.builder()
                .batchConfiguration(batch)
                .stepName(VIRUS_IMPORT_STEP_ID)
                .chunkSize(10)
                .readerContext(fileReaderContext)
                .sourceClass(VirusDto.class)
                .processor(new VirusImportItemProcessor(batch))
                .writerSqlStatement(WRITER_SQL_STATEMENT)
                .build();
        return new StepFactory(stepGeneratorContext).createStep(StepType.DELIMTED_FILE_TO_DB);
    }
}
