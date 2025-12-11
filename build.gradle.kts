plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"

    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.file.batch"
version = "0.0.1-SNAPSHOT"
description = "FileBatch"

val queryDslVersion = "5.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client") // MariaDB JDBC 드라이버 추가
    implementation("com.github.f4b6a3:ulid-creator:5.2.3")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // Jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
    }
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf("-Dfile.encoding=UTF-8")
    // ignoreFailures = true
}