plugins {
    alias(libs.plugins.telepager.android.library)
    alias(libs.plugins.telepager.hilt)
}

android {
    namespace = "ag.sokolov.telepager.core.datastore"
}

dependencies {
    api(libs.androidx.datastore.preferences)

    implementation(projects.core.concurrency)
}
