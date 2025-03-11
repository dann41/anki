plugins {
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":core-api"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.security:spring-security-web")

    // jwt
    val jjwtVersion = "0.12.6"
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    testImplementation(testFixtures(project(":shared")))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test:3.7.4")
}

tasks.bootJar {
    enabled = false
}

description = "auth"