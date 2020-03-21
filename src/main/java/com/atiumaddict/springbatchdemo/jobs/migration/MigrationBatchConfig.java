package com.atiumaddict.springbatchdemo.jobs.migration;

import com.atiumaddict.springbatchdemo.configuration.BatchConfiguration;
import com.atiumaddict.springbatchdemo.factory.listen.JobExecutionListenerDefault;
import com.atiumaddict.springbatchdemo.jobs.migration.steps.importdoctors.DoctorImportImportItemHelper;
import com.atiumaddict.springbatchdemo.jobs.migration.steps.importviruses.VirusImportImportItemHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.atiumaddict.springbatchdemo.utils.JobConstants.MIGRATION_JOB_ID;

@Slf4j
@Configuration
@EnableBatchProcessing
@Import(BatchConfiguration.class)
public class MigrationBatchConfig {

    @Autowired
    private BatchConfiguration batch;


    @Bean(name = MIGRATION_JOB_ID)
    @Qualifier()
    public Job job() {
        return batch.jobBuilderFactory
                .get(MIGRATION_JOB_ID)
                .listener(new JobExecutionListenerDefault(MIGRATION_JOB_ID))
                .incrementer(new RunIdIncrementer())
                .start(new VirusImportImportItemHelper().importViruses(batch))
                .start(new DoctorImportImportItemHelper().importDoctors(batch))
                .build();
    }

    @Bean(name = MIGRATION_JOB_ID + "LauncherTestUtils")
    public JobLauncherTestUtils getJobLauncherTestUtils() {

        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier(MIGRATION_JOB_ID) Job job) {
                super.setJob(job);
            }
        };
    }
}
