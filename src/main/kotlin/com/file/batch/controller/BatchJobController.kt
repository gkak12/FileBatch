package com.file.batch.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/batch")
class BatchJobController(
    private val jobExplorer: JobExplorer
) {

    /**
     * 모든 배치 작업 실행 기록을 조회합니다.
     * 예: GET http://localhost:8080/batch/jobs
     */
    @GetMapping("/jobs")
    fun getAllJobExecutions(): ResponseEntity<Any> {
        val jobNames = jobExplorer.jobNames // 현재까지 실행된 모든 Job 이름 가져오기
        val jobExecutions = jobNames.flatMap { name ->
            jobExplorer
                .getJobInstances(name, 0, jobExplorer.getJobInstanceCount(name).toInt())
                .flatMap { instance ->
                    jobExplorer.getJobExecutions(instance)
                }
        }
        return ResponseEntity.ok(jobExecutions)
    }

    /**
     * 특정 Job ID에 대한 상세 정보를 조회합니다. (로그 포함)
     * 예: GET http://localhost:8080/batch/jobs/1
     */
    @GetMapping("/jobs/{jobExecutionId}")
    fun getJobExecutionDetails(@PathVariable jobExecutionId: Long): ResponseEntity<Any> {
        val jobExecution = jobExplorer.getJobExecution(jobExecutionId)
            ?: return ResponseEntity.notFound().build()

        // 상세 로그를 위해 StepExecution 정보도 포함
        val details = mapOf(
            "jobExecution" to jobExecution,
            "stepExecutions" to jobExecution.stepExecutions // StepExecution은 각 Step의 상세 상태를 포함합니다.
        )
        return ResponseEntity.ok(details)
    }
}