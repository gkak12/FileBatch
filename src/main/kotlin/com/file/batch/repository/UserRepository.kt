package com.file.batch.repository

import com.file.batch.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String>, UserRepositoryDsl {
}