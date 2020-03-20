package com.atiumaddict.springbatchdemo.jobs;

import com.atiumaddict.springbatchdemo.AbstractTest;
import com.atiumaddict.springbatchdemo.dto.InfectionDto;
import com.atiumaddict.springbatchdemo.dto.InfectionType;
import com.atiumaddict.springbatchdemo.factory.read.BeanPropertyRowMapperDefault;
import com.atiumaddict.springbatchdemo.jobs.migration.MigrationBatchConfig;
import com.atiumaddict.springbatchdemo.utils.JobConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.atiumaddict.springbatchdemo.utils.JobConstants.VIRUS_IMPORT_STEP_ID;

@ContextConfiguration(classes = MigrationBatchConfig.class)
@Slf4j
public class MigrationImportsTest extends AbstractTest {

    @Autowired
    @Qualifier(JobConstants.MIGRATION_JOB_ID + "LauncherTestUtils")
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void importVirusesTest() {
        //when
        JobParameters parameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchStep(VIRUS_IMPORT_STEP_ID, parameters);

        List<InfectionDto> infections = jdbcTemplate.query("SELECT * FROM infections", new BeanPropertyRowMapperDefault<>(InfectionDto.class));
        log.info(infections.toString());

        //then
        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
        Assert.assertEquals("Zika Virus", infections.get(0).getName());
        Assert.assertEquals("Coronavirus", infections.get(1).getName());
        Assert.assertNotNull(infections.get(10).getId());
        Assert.assertNotNull(infections.get(13).getDescription());
        Assert.assertEquals("Reovirus", infections.get(15).getName());
        Assert.assertEquals(InfectionType.VIRAL.toString(), infections.get(0).getType());
    }
}
