package com.file.batch.runner

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JobRunner(
    private val jobLauncher: JobLauncher,
    private val importUserJob: Job // BatchConfig에 정의된 Job Bean 이름과 일치해야 함
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println(">>> [JobRunner] Job 실행을 시작합니다...")

        val jobParameters = JobParametersBuilder()
            .addString("job.name", "importUserJob")
            .addString("datetime", LocalDateTime.now().toString()) // 매번 새로운 Job으로 인식하게 함
            .toJobParameters()

        try {
            val execution = jobLauncher.run(importUserJob, jobParameters)
            println(">>> [JobRunner] Job Execution Status: ${execution.status}")
        } catch (e: Exception) {
            println(">>> [JobRunner] Job 실행 중 에러 발생!")
            e.printStackTrace()
        }
    }
}
