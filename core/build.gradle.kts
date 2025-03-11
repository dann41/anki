plugins {
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":core-api"))
    implementation(project(":shared"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.postgresql:postgresql:42.5.1")
    implementation("org.flywaydb:flyway-core:9.8.2")

    implementation("com.vladmihalcea:hibernate-types-60:2.20.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.3")
}

tasks.bootJar {
    enabled = false
}

description = "core"