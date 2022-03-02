import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    group = "dev.mbo"

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // https://developers.google.com/docs/api/how-tos/libraries#java
    implementation("com.google.apis:google-api-services-docs:v1-rev20210707-1.32.1")
    // https://developers.google.com/drive/api/v3/quickstart/java
    implementation("com.google.apis:google-api-services-drive:v3-rev20220110-1.32.1")
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client
    implementation("com.google.api-client:google-api-client:1.33.2")
    // https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client-jetty
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.33.1")
    
    implementation(project(":gitlab4j-api"))

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Test> {
    useJUnitPlatform()
}
