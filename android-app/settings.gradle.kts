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
    }
}

rootProject.name = "OpenWhispr"

include(":app")

// Core modules
include(":core:common")
include(":core:database")
include(":core:network")
include(":core:audio")
include(":core:ai")
include(":core:whisper")
include(":core:model-manager")

// Feature modules
include(":feature:dictation")
include(":feature:assistant")
include(":feature:meetings")
include(":feature:notes")
include(":feature:settings")
include(":feature:chat")
