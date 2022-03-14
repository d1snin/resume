import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "dev.d1s"
version = "1.1.4-stable.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

val kotlinxHtmlVersion: String by project
val teabagsVersion: String by project
val picnicVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("dev.d1s.teabags:teabag-stdlib:$teabagsVersion")
    implementation("dev.d1s.teabags:teabag-spring-logging:$teabagsVersion")
    implementation("dev.d1s.teabags:teabag-spring-web:$teabagsVersion")
    implementation("com.jakewharton.picnic:picnic:$picnicVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
