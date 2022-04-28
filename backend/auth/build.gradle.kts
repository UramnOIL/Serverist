version = "0.1.0"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
    application
}
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "16"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "16"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":backend:common"))

    // kotlin
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)

    // ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.logback)

    // koin
    implementation(libs.koin)

    // exposed
    implementation(libs.bundles.exposed)
    implementation(libs.mysql)

    // springframework
    implementation(libs.springboot.starter.security)
    implementation(libs.springboot.starter.mail)
    implementation(libs.springboot.starter.data.redis)
    implementation("io.ktor:ktor-server-status-pages:2.0.0")
    implementation("io.ktor:ktor-server-call-logging:2.0.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.0")

    // kotest
    testImplementation(libs.test.kotest.property)
    testImplementation(libs.test.kotest.runner.junit5)

    // ktor
    testImplementation(libs.bundles.test.ktor.server)

    // in-memory database
    testImplementation(libs.h2)

    // mail
    testImplementation(libs.test.greenmail)
}

project.setProperty("mainClassName", "io.ktor.server.netty.EngineMain")

tasks.shadowJar {
    manifest {
        attributes(Pair("Main-Class", "io.ktor.server.netty.EngineMain"))
    }
    archiveFileName.set("serverist-server.jar")
}
