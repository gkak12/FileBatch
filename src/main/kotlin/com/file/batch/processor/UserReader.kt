package com.file.batch.processor

import com.file.batch.model.User
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.core.io.FileSystemResource

class UserReader {

    /**
     * CSV 파일을 읽는 FlatFileItemReader 인스턴스를 생성하고 반환합니다.
     * @param inputFilePath 읽을 파일의 시스템 경로
     * @param readerName 배치 메타 데이터에 기록될 Reader의 이름
     * @return FlatFileItemReader<User> 인스턴스
     */
    fun createReader(inputFilePath: String, readerName: String): FlatFileItemReader<User> {
        println(">>> [DEBUG] Reader Input File Path: [$inputFilePath]")

        val resource = FileSystemResource(inputFilePath)
        if (!resource.exists()) {
            println(">>> [ERROR] 파일이 존재하지 않습니다! 경로를 확인해주세요: ${resource.path}")
            throw IllegalArgumentException("입력 파일 경로가 유효하지 않습니다: $inputFilePath")
        } else {
            println(">>> [SUCCESS] 파일을 찾았습니다. 크기: ${resource.contentLength()} bytes")
        }

        return FlatFileItemReaderBuilder<User>()
            .name(readerName) // 배치 메타데이터에 사용될 이름
            .resource(resource)
            .delimited()
            .names("firstName", "lastName", "email")
            .fieldSetMapper(BeanWrapperFieldSetMapper<User>().apply {
                setTargetType(User::class.java)
            })
            .build()
    }
}