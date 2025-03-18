plugins {
    id("application")
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

application {
    mainClass = "com.dann41.anki.cmd.infrastructure.CmdApplication"
}

springBoot {
    mainClass = "com.dann41.anki.cmd.infrastructure.CmdApplication"
}

tasks.getByName("run", JavaExec::class) {
    standardInput = System.`in`
}

dependencies {
    implementation(project(":core-api"))
    implementation(project(":core"))
    implementation(project(":shared"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.postgresql:postgresql:42.7.5")
    implementation("org.flywaydb:flyway-core:11.4.0")

}

tasks.getByName("run", JavaExec::class) {
    standardInput = System.`in`
}

tasks.withType<Test> {
    useJUnitPlatform()
}

description = "cmd"