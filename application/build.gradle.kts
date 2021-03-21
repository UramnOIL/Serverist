plugins {
    kotlin("jvm")
}

dependencies {
    val kotlin_version: String by project

    implementation(":domain")
    implementation(kotlin("stdlib", version = kotlin_version))
}