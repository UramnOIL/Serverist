plugins {
    kotlin("jvm")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    val kotlinVersion: String by project
    val kodeinVersion: String by project
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)
    implementation("org.kodein.di", "kodein-di-jvm", kodeinVersion)
}