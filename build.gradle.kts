import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    id("application")
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.jetbrains.kotlin.kapt") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
}

repositories {
    jcenter()
    maven(url = "https://dl.bintray.com/kordlib/Kord")
}

application {
    mainClassName = "net.willemml.kordbot.BotKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes(Pair("Main-Class", "net.willemml.kordbot.BotKt"))
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("com.gitlab.kordlib.kord:kord-core:0.5.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    implementation("com.gitlab.kordlib.kordx:kordx-commands-runtime-kord:0.2.0")
    kapt("com.gitlab.kordlib.kordx:kordx-commands-processor:0.2.0")

}

sourceSets.main {
    java.srcDirs("src/main")
}