package com.file.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootApplication
@EnableScheduling
class FileBatchApplication(
    private val jobLauncher: JobLauncher,
    private val importUserJob: Job
) {

    @Scheduled(cron = "0 0 2 * * ?") // 예시: 매일 새벽 2시에 실행
    fun runBatchJob() {
        val jobParameters = JobParametersBuilder()
            // 고유한 Job 인스턴스를 위해 현재 시간 파라미터 추가
            .addString("runTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
            .toJobParameters()
        try {
            println("Scheduling batch job '${importUserJob.name}' at ${LocalDateTime.now()}")
            val execution = jobLauncher.run(importUserJob, jobParameters)
            println("Batch Job '${importUserJob.name}' executed with status: ${execution.status}, ID: ${execution.id}")
        } catch (e: Exception) {
            System.err.println("Error executing scheduled batch job '${importUserJob.name}': ${e.message}")
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<FileBatchApplication>(*args)
}
