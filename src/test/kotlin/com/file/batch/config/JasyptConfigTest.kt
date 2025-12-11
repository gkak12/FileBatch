package com.file.batch.config

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class JasyptConfigTest {

    private val log = LoggerFactory.getLogger(JasyptConfigTest::class.java)

    private fun jasyptEncoding(value: String): String {
        val pbeEnc = StandardPBEStringEncryptor()
        pbeEnc.setAlgorithm("PBEWithMD5AndDES")
        pbeEnc.setPassword(System.getenv("JASYPT_KEY"))

        return pbeEnc.encrypt(value)
    }

    private fun jasyptDecoding(value: String): String {
        val pbeEnc = StandardPBEStringEncryptor()
        pbeEnc.setAlgorithm("PBEWithMD5AndDES")
        pbeEnc.setPassword(System.getenv("JASYPT_KEY"))

        return pbeEnc.decrypt(
            value
                .replace("ENC(", "")
                .replace(")", "")
        )
    }

    @Test
    @DisplayName("jasypt 인코딩 테스트")
    fun jasyptEncodingTest(){
        val username = "lmdb_user"
        val password = "lmdb123!"

        log.info("username: {}", jasyptEncoding(username))
        log.info("password: {}", jasyptEncoding(password))
    }

    @Test
    @DisplayName("jasypt 디코딩 테스트")
    fun jasyptDecodingTest(){
        val username = "ENC(CP6FzQYT/Sv52TQi4/9U2Q==)"
        val password = "ENC(36jn6vIZOaywDFHJOUEHGBwjf0ipBYv/)"

        log.info("username: {}", jasyptDecoding(username))
        log.info("password: {}", jasyptDecoding(password))
    }
}