//import org.gradle.kotlin.dsl.testImplementation

plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.onwelo"
version = "0.0.1-SNAPSHOT"
description = "e-election"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

val lombokVersion = "1.18.42"
val mapstructVersion = "1.6.3"
val testcontainersVersion = "1.19.7"

repositories {
    mavenCentral()
}

dependencies {
    //implementation
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")

    //runtimeOnly
    runtimeOnly("org.postgresql:postgresql")

    //compileOnly
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    //annotationProcessor
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    //testImplementation
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-webtestclient")
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))
    testImplementation ("org.testcontainers:postgresql")


    //testRuntimeOnly
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //testCompileOnly
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")

    //testAnnotationProcessor
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
