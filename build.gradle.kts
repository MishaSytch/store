plugins {
	java
	id("org.springframework.boot") version "2.5.15"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "store"
version = "0.0.1"


configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	Spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

//	Data
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")

//  Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")

//  Util
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.apache.commons:commons-lang3")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.springframework.boot:spring-boot-starter-mail")

// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.mockito:mockito-core:5.12.0")
	testImplementation("org.assertj:assertj-core:3.26.3")
	implementation("com.jayway.jsonpath:json-path:2.9.0")
	testImplementation("org.hamcrest:hamcrest:3.0")
	testImplementation ("org.mockito:mockito-core:4.0.0")
	testImplementation("com.icegreen:greenmail:1.6.5")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
