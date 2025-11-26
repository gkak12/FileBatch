package com.file.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FileBatchApplication

fun main(args: Array<String>) {
    runApplication<FileBatchApplication>(*args)
}
