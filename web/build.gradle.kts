plugins {
    kotlin("jvm")
    application
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

dependencies {
    val kotlin_version: String by project
    val ktor_version: String by project
    val logback_version: String by project
    val kgraphql_version: String by project

    implementation(":application")
    implementation(kotlin("stdlib", kotlin_version))
    implementation("io.ktor", "ktor-server-netty", ktor_version)
    implementation("ch.qos.logback", "logback-classic", logback_version)
    implementation("com.apurebase", "kgraphql:", kgraphql_version)
    testImplementation("io.ktor", "ktor-server-tests", ktor_version)
}