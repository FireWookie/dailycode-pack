import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply  false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.compose.plugin) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

group = "ru.dailycode.pack"
version = extra["library_version"].toString()


subprojects {
    extra.set("library_version", getProperty("library_version", "0.1.0"))
    // Репозиторий публикации GitHub Packages + креды из local.properties
    plugins.withId("maven-publish") {
        val localProps = Properties()
        val f = rootProject.file("local.properties")
        if (f.exists()) localProps.load(f.inputStream())
        val gprUser = localProps.getProperty("gpr.user") ?: System.getenv("GPR_USER")
        val gprToken = localProps.getProperty("gpr.token") ?: System.getenv("GPR_TOKEN")

        extensions.configure<PublishingExtension>("publishing") {
            repositories {
                maven {
                    name = "githubPackages"
                    url = uri("https://maven.pkg.github.com/firewookie/dailycode-pack")
                    credentials {
                        username = gprUser
                        password = gprToken
                    }
                }
            }
        }
    }
}

fun Project.getProperty(name: String, defaultValue: String): String {
    return getProperty(name) ?: defaultValue
}

fun Project.getProperty(name: String): String? {
    return getEnvironmentProperty(name)
        ?: rootProject.getLocalProperty(name)
        ?: rootProject.findProperty(name)?.toString()
}

fun getEnvironmentProperty(name: String, defaultValue: String): String? {
    return getEnvironmentProperty(name) ?: defaultValue
}

fun getEnvironmentProperty(name: String): String? {
    return System.getenv()[name]
}

fun Project.getLocalProperty(name: String, defaultValue: String): String {
    return getLocalProperty(name) ?: defaultValue
}

fun Project.getLocalProperty(name: String): String? {
    return getLocalProperties().getProperty(name)
}

fun Project.getLocalProperties(): Properties {
    return loadLocalProperties()
}

fun Project.loadLocalProperties(fileName: String = "local.properties"): Properties {
    return Properties().also { properties ->
        try {
            file(fileName).inputStream().use { properties.load(it) }
        } catch (e: Exception) {
            logger.info("$fileName not found, skip loading properties")
        }
    }
}