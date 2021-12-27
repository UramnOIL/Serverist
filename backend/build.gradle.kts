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
    implementation(project(":domain:common"))
    implementation(project(":domain:auth"))
    implementation(project(":domain:serverist"))

    implementation(project(":application", "serverJvmRuntimeElements"))

    // kotlin
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)

    // ktor
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.serialization)
    implementation(libs.logback)

    // kgraphql
    implementation(libs.kgraphql)
    implementation(libs.kgraphql.ktor)

    // koin
    implementation(libs.koin)

    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
    implementation(libs.mysql)

    // springframework
    implementation(libs.springboot.starter.security)
    implementation(libs.springboot.starter.mail)
    implementation(libs.springboot.starter.data.redis)

    // test

    // mockk
    testImplementation("io.mockk:mockk:1.12.0")

    // kotest
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotest.runner.junit5)

    // ktor
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.ktor.server.test.host)

    // in-memory database
    testImplementation(libs.h2)

    // mail

    testImplementation("com.icegreen:greenmail:1.6.5")
}

project.setProperty("mainClassName", "io.ktor.server.netty.EngineMain")

tasks.shadowJar {
    manifest {
        attributes(Pair("Main-Class", "io.ktor.server.netty.EngineMain"))
    }
}
