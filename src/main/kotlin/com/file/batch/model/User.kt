package com.file.batch.model

data class User(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null
) {
    constructor() : this(null, null, null)
}
