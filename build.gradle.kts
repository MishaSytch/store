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

//	Data
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")

//  Util
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.apache.commons:commons-lang3")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
