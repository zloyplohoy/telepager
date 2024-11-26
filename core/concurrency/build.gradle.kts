plugins {
    alias(libs.plugins.telepager.jvm.library)
    alias(libs.plugins.telepager.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
