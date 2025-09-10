plugins {
    id("application")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

application {
    mainClass = "com.dann41.anki.api.infrastructure.ApiApplication"
}

springBoot {
    mainClass = "com.dann41.anki.api.infrastructure.ApiApplication"
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":auth"))
    implementation(project(":core"))
    implementation(project(":core-api"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.security:spring-security-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.postgresql:postgresql:42.7.7")
    implementation("org.flywaydb:flyway-core:11.12.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test:3.7.9")
}

tasks.getByName("run", JavaExec::class) {
    standardInput = System.`in`
}

tasks.withType<Test> {
    useJUnitPlatform()
}

description = "api"