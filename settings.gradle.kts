import java.util.Properties



pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()

        val localProps = Properties()
        val localPropsFile = rootDir.resolve("local.properties")
        if (localPropsFile.exists()) {
            localProps.load(localPropsFile.inputStream())
        }

        val gprUser = localProps.getProperty("gpr.user") ?: System.getenv("GPR_USER")
        val gprToken = localProps.getProperty("gpr.token") ?: System.getenv("GPR_TOKEN")

        maven {
            url = uri("https://maven.pkg.github.com/firewookie/dailycode-pack")
            credentials {
                username = gprUser
                password = gprToken
            }
        }
    }
}

rootProject.name = "ru.dailycode.pack"
include(":compose-ext")
include(":decompose-ext")
include(":sample:composeApp")
