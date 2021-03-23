plugins {
    kotlin("jvm")
    id("com.squareup.sqldelight")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    val kotlin_version: String by project
    val kodein_version: String by project
    val coroutines_version: String by project
    val sqldelight_version: String by project

    implementation(kotlin("stdlib", version = kotlin_version))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutines_version)
    implementation("org.kodein.di", "kodein-di-jvm", kodein_version)
    implementation("com.squareup.sqldelight", "sqlight-driver", sqldelight_version)
}