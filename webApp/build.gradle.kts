import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
                // Modern webpack configuration
                cssSupport {
                    enabled.set(true)
                }
            }
            // Enable development server with hot reload
            runTask {
                devServer = org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 8080,
                    proxy = null,
                    static = mutableListOf("$projectDir/src/jsMain/resources")
                )
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
                implementation(projects.shared)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.8.1")
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

compose.experimental {
    web.application {}
}