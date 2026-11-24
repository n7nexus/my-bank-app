plugins {
	java
	id("org.springframework.boot") version "4.1.2"
	id("io.spring.dependency-management") version "1.1.7"

	id("org.springframework.cloud.contract") version "5.0.1"
	id("maven-publish")
}

group = "ru.practicum"
version = "0.0.1-SNAPSHOT"

val mapstructVersion = "1.6.3"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.cloud:spring-cloud-starter-consul-config")
	implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webclient")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-liquibase")
	implementation("org.mapstruct:mapstruct:$mapstructVersion")

	compileOnly("org.projectlombok:lombok")

	runtimeOnly("org.postgresql:postgresql")


	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

//	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:5.0.1")
	testImplementation("io.rest-assured:spring-mock-mvc:6.3.0") {
		exclude(group = "org.springframework", module = "spring-webmvc")
		exclude(group = "org.springframework", module = "spring-test")
	}
	testImplementation("com.h2database:h2")

	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
}


extra["springCloudVersion"] = "2025.1.4"

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			groupId = "com.practicum"
			artifactId = "account-service"
			version = "0.0.1-SNAPSHOT"

			from(components["java"])
			artifact(tasks.named("verifierStubsJar"))
		}
	}
	repositories {
		mavenLocal()
	}
}

contracts {
	baseClassForTests.set(
		"com.practicum.accounts.contract.BaseAccountContractTest"
	)
}

tasks.withType<org.gradle.api.publish.maven.tasks.GenerateMavenPom>().configureEach {
	notCompatibleWithConfigurationCache("maven-publish GenerateMavenPom is not fully CC compatible")
}