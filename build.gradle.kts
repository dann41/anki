plugins {
    idea
    java
}

group = "com.github.dann41"
version = "1.0-SNAPSHOT"

subprojects {
    apply(plugin = "java")

    group = project.group
    version = project.version

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        // bom junit jupiter api
        testImplementation(platform("org.junit:junit-bom:5.12.2"))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.junit.jupiter:junit-jupiter-params")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
        testImplementation("org.assertj:assertj-core:3.27.3")
        testImplementation("org.mockito:mockito-core:5.17.0")
        testImplementation("org.mockito:mockito-junit-jupiter:5.17.0")
    }
}