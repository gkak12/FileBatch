package com.file.batch.processor

import com.file.batch.model.User
import org.springframework.batch.item.ItemProcessor

class UserProcessor : ItemProcessor<User, User> {

    // read: User, write: User
    override fun process(user: User): User? {
        // 데이터 처리 로직: 이름을 모두 대문자로 변환 (null 체크 포함)
        val firstName = user.firstName?.uppercase()
        val lastName = user.lastName?.uppercase()

        val transformedUser = User(
            firstName = firstName,
            lastName = lastName,
            email = user.email
        )

        println("Converting (${user}) into (${transformedUser})")

        return transformedUser
    }
}
