plugins {
    alias(libs.plugins.telepager.jvm.library)
    alias(libs.plugins.telepager.hilt)
}

dependencies {
    api(libs.kotlinx.coroutines.core)
}
