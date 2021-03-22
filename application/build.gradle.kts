plugins {
    kotlin("jvm")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    val kotlin_version: String by project
    val kodein_version: String by project

    implementation(project(":domain"))
    implementation(kotlin("stdlib", version = kotlin_version))
    implementation("org.kodein.di", "kodein-di-jvm", kodein_version)
}