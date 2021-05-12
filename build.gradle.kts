group = "com.uramnoil"
version = "0.0.1"

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.38.0"
}

allprojects {
    repositories {
        mavenLocal()
    }
}
