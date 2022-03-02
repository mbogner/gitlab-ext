plugins {
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    `java-library`
}

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    api("jakarta.activation:jakarta.activation-api")
    api("com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider")
    api("jakarta.servlet:jakarta.servlet-api")
    implementation("org.slf4j:slf4j-api")

    // jersey
    api("org.glassfish.jersey.inject:jersey-hk2:2.35")
    api("org.glassfish.jersey.core:jersey-client:2.35")
    api("org.glassfish.jersey.connectors:jersey-apache-connector:2.35")
    api("org.glassfish.jersey.media:jersey-media-multipart:2.35")
}