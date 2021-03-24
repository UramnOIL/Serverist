plugins {
    kotlin("jvm")
    application
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    mavenCentral()
}

dependencies {
    val kotlinVersion: String by project
    val coroutinesVersion: String by project
    val ktorVersion: String by project
    val logbackVersion: String by project
    val kgraphqlVersion: String by project
    val kodeinVersion: String by project
    val exposedVersion: String by project

    implementation(project(":application"))
    implementation(project(":domain"))
    implementation(kotlin("stdlib", kotlinVersion))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)
    implementation("io.ktor", "ktor-server-netty", ktorVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)
    implementation("com.apurebase", "kgraphql", kgraphqlVersion)
    implementation("org.kodein.di", "kodein-di-jvm", kodeinVersion)
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("com.h2database", "h2", "1.4.200")

    testImplementation("io.ktor", "ktor-server-tests", ktorVersion)
}