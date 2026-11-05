dependencies {
//	implementation(libs.spring.cloud.starter.gateway.server.webflux)

    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.oauth2.client)
    implementation(libs.spring.boot.starter.oauth2.resource.server)


    implementation(libs.spring.boot.starter.actuator)

    testImplementation(libs.spring.boot.starter.test)
}


////extra["springCloudVersion"] = "2025.1.4"
//
//dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("org.springframework.boot:spring-boot-starter-webmvc")
//	implementation("org.springframework.cloud:spring-cloud-starter-consul-config")
//	implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
//	implementation("org.springframework.cloud:spring-cloud-starter-gateway-server-webflux")
//	compileOnly("org.projectlombok:lombok")
//	annotationProcessor("org.projectlombok:lombok")
//	testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
//	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
//	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
//	testCompileOnly("org.projectlombok:lombok")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//	testAnnotationProcessor("org.projectlombok:lombok")
//}
//
////dependencyManagement {
////	imports {
////		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
////	}
////}
//
tasks.withType<Test> {
	useJUnitPlatform()
}
