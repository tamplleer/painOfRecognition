plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.openjfx:javafx-controls:16")
    implementation("org.openjfx:javafx-fxml:16")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(14)
}

application {
    mainClass.set("MainKt")
}
javafx {
    version = "12"
    modules("javafx.controls", "javafx.fxml")
}