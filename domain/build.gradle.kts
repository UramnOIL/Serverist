plugins {
    kotlin("jvm")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
}

dependencies {
    val kotlin_version: String by project
    implementation(kotlin("stdlib", version = kotlin_version))
}