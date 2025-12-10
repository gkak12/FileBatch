package com.file.batch.model

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.Table

@Entity
@Table(schema = "batch", name = "user_info")
data class User(
    @Id
    @Column(length = 26)
    var id: String? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "email")
    var email: String? = null
) {
    constructor() : this(null, null, null, null)

    @PrePersist
    fun generateUlid() {
        if (id == null) {
            // ULID 라이브러리를 사용
            this.id = UlidCreator.getUlid().toString()
        }
    }
}
