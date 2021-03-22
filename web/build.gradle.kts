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
    val kotlin_version: String by project
    val coroutines_version: String by project
    val ktor_version: String by project
    val logback_version: String by project
    val kgraphql_version: String by project
    val kodein_version: String by project

    implementation(project(":application"))
    implementation(kotlin("stdlib", kotlin_version))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutines_version)
    implementation("io.ktor", "ktor-server-netty", ktor_version)
    implementation("ch.qos.logback", "logback-classic", logback_version)
    implementation("com.apurebase", "kgraphql", kgraphql_version)
    implementation("org.kodein.di", "kodein-di-jvm", kodein_version)
    testImplementation("io.ktor", "ktor-server-tests", ktor_version)
}