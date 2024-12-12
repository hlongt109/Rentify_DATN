import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.authentication.http.BasicAuthentication

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password =
                    "sk.eyJ1IjoicGhvbmdiYW90byIsImEiOiJjbTRmd2w5dGsxODlvMmxwZnY4NDFybGYzIn0.v5PXUGm7q7E-3lU17pWLhw"
            }
        }
        maven(url = "https://jitpack.io")
    }

}

rootProject.name = "Rentify"
include(":app")
