plugins {
    alias(libs.plugins.telepager.android.library)
    alias(libs.plugins.telepager.hilt)
}

android {
    namespace = "ag.sokolov.telepager.core.service"
}

dependencies {
    implementation(projects.core.concurrency)
    implementation(projects.core.data)
    implementation(projects.core.telegram)
}
