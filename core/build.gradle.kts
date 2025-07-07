plugins {
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation(project(":core-api"))
    implementation(project(":shared"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.postgresql:postgresql:42.7.5")
    implementation("org.flywaydb:flyway-core:11.10.1")

    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.0")
}

tasks.bootJar {
    enabled = false
}

description = "core"