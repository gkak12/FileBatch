package com.file.batch.repository.impl

import org.springframework.stereotype.Repository
import com.querydsl.jpa.impl.JPAQueryFactory
import com.file.batch.repository.UserRepositoryDsl

@Repository
class UserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): UserRepositoryDsl {
}