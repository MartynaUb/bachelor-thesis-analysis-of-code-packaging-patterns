
plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.2.3"
}

group = "lt.mif"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.javaparser:javaparser-core:3.25.10")
    implementation("guru.nidi:graphviz-java-all-j2v8:0.18.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

ktor {
    fatJar {
        archiveFileName.set("system-analyzer.jar")
    }
}

application {
    mainClass.set("lt.mif.MainKt")
}