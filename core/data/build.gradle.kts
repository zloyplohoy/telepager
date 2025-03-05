plugins {
    alias(libs.plugins.telepager.android.library)
    alias(libs.plugins.telepager.koin)
}

android {
    namespace = "ag.sokolov.telepager.core.data"
}

dependencies {
    api(projects.core.database)
    api(projects.core.result)
    implementation(projects.core.concurrency)
    implementation(projects.core.telegram)
}
