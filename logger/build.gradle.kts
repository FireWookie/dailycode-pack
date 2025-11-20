@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

version = extra["library_version"].toString()

private val KotlinMultiplatformExtension.desktopTargetList: List<KotlinNativeTarget>
    get() = listOf(
        linuxArm64(),
        linuxX64(),
        mingwX64(),
    )

private val KotlinMultiplatformExtension.iosTargetList: List<KotlinNativeTarget>
    get() = listOf(
        iosSimulatorArm64(),
        iosArm64(),
        iosX64(),
    )
private val KotlinMultiplatformExtension.macOsTargetList: List<KotlinNativeTarget>
    get() = listOf(
        macosArm64(),
        macosX64(),
    )

private val KotlinMultiplatformExtension.watchOsTargetList: List<KotlinNativeTarget>
    get() = listOf(
        watchosSimulatorArm64(),
        watchosX64(),
        watchosArm32(),
        watchosArm64(),
        watchosDeviceArm64()
    )

private val KotlinMultiplatformExtension.tvOsTargetList: List<KotlinNativeTarget>
    get() = listOf(
        tvosSimulatorArm64(),
        tvosX64(),
        tvosArm64(),
    )

private val KotlinMultiplatformExtension.nativeTargetList: List<KotlinNativeTarget>
    get() = iosTargetList + macOsTargetList + watchOsTargetList + tvOsTargetList

private val KotlinMultiplatformExtension.androidNativeTargetList: List<KotlinNativeTarget>
    get() = listOf(
        androidNativeArm32(),
        androidNativeArm64(),
        androidNativeX86(),
        androidNativeX64(),
    )
private fun KotlinMultiplatformExtension.applyAllWebTargets() {
    js().browser()
    wasmJs().browser()
//    wasmWasi().nodejs()
}
private fun KotlinMultiplatformExtension.applyAllAvailableTargets(
) {
    jvm()

    nativeTargetList.forEach { /* target created by list initialization */ }
    androidNativeTargetList.forEach { /* same */ }
    desktopTargetList.forEach { /* same */ }

    applyAllWebTargets()
}
kotlin {
    explicitApiWarning()

    // Jvm
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    applyAllAvailableTargets()
    wasmJs { browser() }
    wasmWasi { nodejs() }

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
        }
    }
}

android {
    namespace = "ru.dailycode.pack.logger"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    coordinates("ru.dailycode.pack", "logger", version.toString())

    pom {
        name = "DailyCodePack"
        description = "daily code ui pack compose-ext"
        inceptionYear = "2024"
        url = "https://github.com/firewookie/dailycode-pack"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "dailycode"
                name = "Daily Code"
                url = "https://github.com/firewookie"
            }
        }
        scm {
            url = "https://github.com/firewookie/dailycode-pack"
            connection = "scm:git:git://github.com/firewookie/dailycode-pack.git"
            developerConnection = "scm:git:ssh://github.com/firewookie/dailycode-pack.git"
        }
    }
}
