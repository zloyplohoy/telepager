plugins {
    alias(libs.plugins.telepager.android.library)
    alias(libs.plugins.telepager.android.room)
    alias(libs.plugins.telepager.koin)
}

android {
    namespace = "ag.sokolov.telepager.core.database"
}

dependencies {
    api(projects.core.model)
}
