package com.atiumaddict.springbatchdemo.jobs.migration.steps.importdoctors;

import com.atiumaddict.springbatchdemo.configuration.BatchConfiguration;
import com.atiumaddict.springbatchdemo.dto.DoctorDto;
import com.atiumaddict.springbatchdemo.factory.StepFactory;
import com.atiumaddict.springbatchdemo.factory.StepGeneratorContext;
import com.atiumaddict.springbatchdemo.factory.read.FileReaderContext;
import com.atiumaddict.springbatchdemo.factory.stepgenerators.StepType;
import com.atiumaddict.springbatchdemo.utils.JobUtils;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import static com.atiumaddict.springbatchdemo.utils.JobConstants.DOCTOR_IMPORT_STEP_ID;
import static com.atiumaddict.springbatchdemo.utils.JobUtils.BASEPATH_DIR;

public class DoctorImportImportItemHelper {

    private static final String WRITER_SQL_STATEMENT =
            "INSERT INTO doctors (" +
                    "id, first_name, last_name, gender" +
                    ") \n" +
                    "VALUES (" +
                    ":id, :firstName, :lastName, :gender" +
                    ")";

    @Bean
    @Qualifier(DOCTOR_IMPORT_STEP_ID)
    public Step importDoctors(BatchConfiguration batch) {
        FileReaderContext fileReaderContext = new FileReaderContext().builder()
                .defaultFileResource(JobUtils.getLatestResourceByFilePattern(BASEPATH_DIR, "*Doctors*"))
                .ranges(new Range[]{new Range(1, 10), new Range(11, 35), new Range(36, 60), new Range(61, 80)})
                .fieldNames(new String[]{"id", "firstName", "lastName", "gender"})
                .fieldSetMapper(new DoctorFieldSetMapper())
                .build();
        StepGeneratorContext stepGeneratorContext = StepGeneratorContext.builder()
                .batchConfiguration(batch)
                .stepName(DOCTOR_IMPORT_STEP_ID)
                .chunkSize(10)
                .readerContext(fileReaderContext)
                .sourceClass(DoctorDto.class)
                .writerSqlStatement(WRITER_SQL_STATEMENT)
                .build();
        return new StepFactory(stepGeneratorContext).createStep(StepType.FIXED_LENGTH_FILE_TO_DB);
    }
}
