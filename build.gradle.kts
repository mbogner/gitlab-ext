import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "dev.mbo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
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
    
    // local build of https://mvnrepository.com/artifact/org.gitlab4j/gitlab4j-api
    implementation(files("libs/gitlab4j-api-4.20.0-SNAPSHOT.jar"))
    implementation("jakarta.activation:jakarta.activation-api")
    implementation("com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider")
    implementation("jakarta.servlet:jakarta.servlet-api")
    // jersey
    implementation("org.glassfish.jersey.inject:jersey-hk2:2.35")
    implementation("org.glassfish.jersey.core:jersey-client:2.35")
    implementation("org.glassfish.jersey.connectors:jersey-apache-connector:2.35")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:2.35")
    // https://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j
    implementation("org.slf4j:jul-to-slf4j:1.7.36")

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

tasks.register<Copy>("updateGitlab4j") {
    from(file("/Users/manuel/workspace/private/gitlab4j-api/target/gitlab4j-api-4.20.0-SNAPSHOT.jar"))
    into(layout.projectDirectory.dir("libs"))
}

