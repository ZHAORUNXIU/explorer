import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.12"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
//	kotlin("jvm") version "1.6.21"
	kotlin("jvm") version "1.7.22"
//	kotlin("plugin.spring") version "1.6.21"
//	kotlin("plugin.jpa") version "1.6.21"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
}

group = "com.crypted"
version = "0.0.1-SNAPSHOT"

java {
//	sourceCompatibility = JavaVersion.VERSION_1_8
		sourceCompatibility = JavaVersion.VERSION_17

}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
//		jvmTarget = "1.8"
		jvmTarget = "17"
	}
}
//
//tasks.withType<Test> {
//	useJUnitPlatform()
//}
