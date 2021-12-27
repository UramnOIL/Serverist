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
    implementation(libs.bundles.ktor.server)

    implementation(libs.logback)

    // kgraphql
    implementation(libs.kgraphql)
    implementation(libs.kgraphql.ktor)

    // koin
    implementation(libs.koin)

    // exposed
    implementation(libs.bundles.exposed)
    implementation(libs.mysql)

    // springframework
    implementation(libs.springboot.starter.security)
    implementation(libs.springboot.starter.mail)
    implementation(libs.springboot.starter.data.redis)

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
}
