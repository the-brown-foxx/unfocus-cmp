plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.exposed.core)
            implementation(libs.exposed.jdbc)
            implementation(libs.sqlite.jdbc)
            implementation(libs.exposed.kotlin.datetime)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}