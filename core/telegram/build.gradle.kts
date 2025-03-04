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
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.resources)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
}
