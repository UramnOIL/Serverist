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
    val kotlinVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project
    val ktorVersion: String by project
    val logbackVersion: String by project
    val kgraphqlVersion: String by project
    val koinVersion: String by project
    val exposedVersion: String by project
    val springBootVersion: String by project
    val kotestVersion: String by project

    implementation(project(":domain:common"))
    implementation(project(":domain:auth"))
    implementation(project(":domain:serverist"))

    implementation(project(":application", "serverJvmRuntimeElements"))

    // kotlin
    implementation(kotlin("stdlib", kotlinVersion))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", serializationVersion)

    // ktor
    implementation("io.ktor", "ktor-server-netty", ktorVersion)
    implementation("io.ktor", "ktor-auth", ktorVersion)
    implementation("io.ktor", "ktor-server-sessions", ktorVersion)
    implementation("io.ktor", "ktor-serialization", ktorVersion)
    implementation("ch.qos.logback", "logback-classic", "1.2.6")

    // kgraphql
    implementation("com.apurebase", "kgraphql", kgraphqlVersion)
    implementation("com.apurebase", "kgraphql-ktor", kgraphqlVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)

    // koin
    implementation("io.insert-koin", "koin-ktor", koinVersion)

    // exposed
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-java-time", exposedVersion)
    implementation("mysql", "mysql-connector-java", "8.0.20")

    // springframework
    implementation("org.springframework.boot", "spring-boot-starter-security", springBootVersion)
    implementation("org.springframework.boot", "spring-boot-starter-mail", springBootVersion)
    implementation("org.springframework.boot", "spring-boot-starter-data-redis", springBootVersion)

    // test

    // mockk
    testImplementation("io.mockk", "mockk", "1.12.0")

    // kotest
    testImplementation("io.kotest", "kotest-property", kotestVersion)
    testImplementation("io.kotest", "kotest-runner-junit5-jvm", kotestVersion)

    // ktor
    testImplementation("io.ktor", "ktor-server-tests", ktorVersion)
    testImplementation("io.ktor", "ktor-server-test-host", ktorVersion)

    // in-memory database
    testImplementation("com.h2database", "h2", "1.4.200")

    // mail

    testImplementation("com.icegreen", "greenmail", "1.6.5")
}

project.setProperty("mainClassName", "io.ktor.server.netty.EngineMain")

tasks.shadowJar {
    manifest {
        attributes(Pair("Main-Class", "io.ktor.server.netty.EngineMain"))
    }
}

