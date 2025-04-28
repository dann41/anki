plugins {
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation(project(":shared"))
}

tasks.bootJar {
    enabled = false
}

description = "core-api"