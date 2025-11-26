import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.compose.plugin)
    alias(libs.plugins.compose.compiler)
}

version = extra["library_version"].toString()

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(project(":logger"))

                api(libs.decompose)
                api(libs.decompose.compose)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "ru.dailycode.pack.decompose_ext"
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
    coordinates("ru.dailycode.pack", "decompose-ext", version.toString())

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