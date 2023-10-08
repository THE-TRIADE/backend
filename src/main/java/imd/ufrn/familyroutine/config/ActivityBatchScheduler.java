package imd.ufrn.familyroutine.config;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.repository.ActivityRepository;
import jakarta.persistence.EntityManagerFactory;

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
    private ActivityRepository activityRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

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
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    public AtomicInteger getBatchRunCounter() {
        return batchRunCounter;
    }

    @Bean
    public ItemReader<Activity> reader() {
        return new JpaCursorItemReaderBuilder<Activity>()
            .name("activityItemReader")
            .queryString("SELECT a FROM Activity a") // This is in JPQL, not SQL
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public ItemProcessor<Activity, Activity> processor() {
        return new ActivityItemProcessor();
    }

    @Bean
    public ItemWriter<Activity> writer() {
        return activities -> {
            activityRepository.saveAll(activities);
        };
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
