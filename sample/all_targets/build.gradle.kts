@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.application)
}

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
    js {
        outputModuleName = "shared"
        browser()
        binaries.library()
        generateTypeScriptDefinitions()
        compilerOptions {
            target = "es2015"
        }
    }
}
private fun KotlinMultiplatformExtension.applyAllAvailableTargets(
) {
    jvm()

    nativeTargetList.forEach { _ -> /* target created by list initialization */ }
    androidNativeTargetList.forEach { _ -> /* same */ }
    desktopTargetList.forEach { _ -> /* same */ }

    applyAllWebTargets()
}
kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    applyAllAvailableTargets()
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(project(":logger"))
        }
    }
    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach {
            binaries {
                framework {
                    baseName = "Shared"
                    isStatic = true
                }
            }
        }
}

android {
    namespace = "ru.dailycode.pack.all_targets"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "ru.dailycode.pack.all_targets"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

//compose.desktop {
//    application {
//        mainClass = "MainTestKt"
//
//        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
//            packageName = "AllTargets"
//            packageVersion = "1.0.0"
//
//            linux {
//                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
//            }
//            windows {
//                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
//                dirChooser = true
//                shortcut = true
//                menu = true
//                console = false
//                perUserInstall = false
//                includeAllModules = true
//
//                // Add JVM arguments for admin privileges and file permissions
//                jvmArgs += listOf(
//                    "-Dfile.encoding=UTF-8",
//                    "--add-opens", "java.base/java.lang=ALL-UNNAMED",
//                    "--add-opens", "java.base/java.io=ALL-UNNAMED"
//                )
//
//                // Add resources directory for manifest
//                appResourcesRootDir.set(project.layout.projectDirectory.dir("src/main/resources"))
//            }
//            macOS {
//                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
//                bundleID = "com.app.multiconfig.desktopApp"
//            }
//        }
//    }
//}
