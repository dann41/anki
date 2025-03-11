plugins {
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":shared"))
}

tasks.bootJar {
    enabled = false
}

description = "core-api"