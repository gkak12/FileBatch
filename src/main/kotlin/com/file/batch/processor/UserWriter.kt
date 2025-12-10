package com.file.batch.processor

import com.file.batch.model.User
import com.file.batch.repository.UserRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserWriter (
    private val userRepository: UserRepository
): ItemWriter<User> {

    private val log = LoggerFactory.getLogger(UserWriter::class.java)

    override fun write(chunk: Chunk<out User>) {
        log.info("--- Writer Result (Chunk Size: {}) ---", chunk.size())
        // chunk.items를 통해 List에 접근합니다.
        chunk.items.forEach { user ->
            // 처리된 사용자 정보를 콘솔에 출력
            log.info("사용자 정보: {}", user.toString())
        }

        userRepository.saveAll(chunk.items)
        log.info("-------------------------------------")
    }
}