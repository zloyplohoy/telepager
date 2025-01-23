plugins {
    alias(libs.plugins.telepager.jvm.library)
    alias(libs.plugins.telepager.hilt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(projects.core.result)

    implementation(projects.core.concurrency)
    implementation(projects.core.model)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
}
