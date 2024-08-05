plugins {
	java
	id("org.springframework.boot") version "2.5.15"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "store"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(8)
	}
}

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
	testImplementation("org.springframework.boot:spring-boot-starter-test")

//	Data
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")

//	Security
//	implementation("org.springframework.boot:spring-boot-starter-security")
//	testImplementation("org.springframework.security:spring-security-test")

//	Test
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//	etc
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
