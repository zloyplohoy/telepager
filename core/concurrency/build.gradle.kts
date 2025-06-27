plugins {
    alias(libs.plugins.telepager.jvm.library)
    alias(libs.plugins.telepager.koin)
}

dependencies {
    api(libs.kotlinx.coroutines.core)
}
