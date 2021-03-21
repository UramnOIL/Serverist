plugins {
    kotlin("jvm")
}

dependencies {
    val kotlin_version: String by project
    implementation(kotlin("stdlib", version = kotlin_version))
}