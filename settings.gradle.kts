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

rootProject.name = "BENKKSTUDIO CORE"
include(":app")
include(":bee-core")
include(":bee-ads")
include(":bee-admob")
include(":bee-consent")
include(":bee-prefs")
include(":bee-max")
