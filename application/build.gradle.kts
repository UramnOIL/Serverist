plugins {
    kotlin("jvm")
}

dependencies {
    val kotlin_version: String by project
    val kodein_version: String by project

    implementation(project(":domain"))
    implementation(kotlin("stdlib", version = kotlin_version))
    implementation("org.kodein.di", "kodein-di-jvm", kodein_version)
}