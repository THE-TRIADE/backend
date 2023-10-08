package imd.ufrn.familyroutine.config;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.repository.mappers.ActivityMapper;

@Configuration
@EnableScheduling
public class ActivityBatchScheduler  {
    private final Logger logger = LoggerFactory.getLogger(ActivityBatchScheduler.class);
    private AtomicBoolean enabled = new AtomicBoolean(true);
    private AtomicInteger batchRunCounter = new AtomicInteger(0);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private DataSource dataSource;

    @Scheduled(fixedRate = 60000, initialDelay = 40000)
    public void launchJob() throws Exception {
        LocalDateTime date = LocalDateTime.now();
        if (enabled.get()) {
            JobExecution jobExecution = jobLauncher.run(activityJob(jobRepository, transactionManager), 
                    new JobParametersBuilder().addLocalDateTime("launchDate", date).toJobParameters());
            batchRunCounter.incrementAndGet();
            logger.info("Batch job ends with status as " + jobExecution.getStatus());
        }
    }

    public void stop() {
        enabled.set(false);
    }

    public void start() {
        enabled.set(true);
    }

    @Bean
    public Job activityJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("activityJob", jobRepository)
                .start(checkActivityState(jobRepository, transactionManager))
                .build();
    }

    @Bean
    protected Step checkActivityState(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("checkActivityState", jobRepository)
                .<Activity,Activity> chunk(5, transactionManager)
                .reader(reader(dataSource))
                .processor(processor())
                .writer(writer(dataSource))
                .build();
    }

    public AtomicInteger getBatchRunCounter() {
        return batchRunCounter;
    }

    @Bean
    public ItemReader<Activity> reader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Activity>()
            .name("activityItemReader")
            .verifyCursorPosition(false)
            .dataSource(dataSource)
            .sql("SELECT * FROM ACTIVITY")
            .rowMapper(new ActivityMapper())
            .build();
    }

    @Bean
    public ItemProcessor<Activity, Activity> processor() {
        return new ActivityItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Activity> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Activity>()
          .itemSqlParameterSourceProvider(parameterSourceProvider())
          .sql("UPDATE `activity` SET `state` = :state WHERE id = :id")
          .dataSource(dataSource)
          .build();
    }

    public BeanPropertyItemSqlParameterSourceProvider<Activity> parameterSourceProvider () {
        return new BeanPropertyItemSqlParameterSourceProvider<Activity>() {

            @Override
            public SqlParameterSource createSqlParameterSource(Activity item) {
                return new BeanPropertySqlParameterSource(item) {
                    @Override
                    public Object getValue(String paramName) throws IllegalArgumentException {
                        Object value = super.getValue(paramName);
                        if (value instanceof Enum) {
                            return value.toString();
                        }
                        return value;
                    }
                };
            }
        };
    }
}
