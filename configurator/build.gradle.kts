import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.buildkonfig)
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

buildkonfig {
    packageName = "com.thebrownfoxx.unfocus.configurator"

    defaultConfigs {
        val debug = System.getenv("DEBUG") ?: "false"
        buildConfigField(FieldSpec.Type.BOOLEAN, "DEBUG", debug)
    }
}