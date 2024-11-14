import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
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
            implementation(libs.kotlinx.datetime)
            implementation(libs.kdiscordipc)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

buildkonfig {
    packageName = "com.thebrownfoxx.unfocus.presence"

    defaultConfigs {
        val discordClientId = gradleLocalProperties(rootDir.resolve("presence"), providers)
            .getProperty("discord-client-id")

        buildConfigField(FieldSpec.Type.STRING, "DISCORD_CLIENT_ID", discordClientId)
    }
}