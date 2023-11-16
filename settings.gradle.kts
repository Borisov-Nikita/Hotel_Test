pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Hotel Test"
include(":app")
include(":features:hotel")
include(":features:rooms")
include(":features:booking")
include(":features:paid")
include(":core:presentation")
include(":core:theme")
