package com.file.batch.config

import com.file.batch.model.User
import com.file.batch.processor.UserProcessor
import com.file.batch.processor.UserReader
import com.file.batch.processor.UserWriter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class BatchConfig {

    // --- Reader (읽기) ---
    @Bean
    fun reader(@Value("\${file.input}") inputFilePath: String): FlatFileItemReader<User> {
        return UserReader().createReader(inputFilePath, "userReader")
    }

    // --- Processor (처리) ---
    @Bean
    fun processor() = UserProcessor()

    // --- Writer (쓰기 - 콘솔 출력) ---
    @Bean
    fun writer(): ItemWriter<User> {
        return UserWriter()
    }

    // --- Step (단계) 정의 ---
    @Bean
    fun step1(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        reader: FlatFileItemReader<User>,
        processor: UserProcessor,
        writer: ItemWriter<User>
    ): Step {
        return StepBuilder("step1", jobRepository)
            // <Input Type, Output Type>
            .chunk<User, User>(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }

    // --- Job (작업) 정의 ---
    @Bean
    fun importUserJob(jobRepository: JobRepository, step1: Step): Job {
        return JobBuilder("importUserJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .flow(step1)
            .end()
            .build()
    }
}
