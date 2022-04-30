import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

val kotlinVersion = "1.6.10"
val serializationVersion = "1.3.2"
val ktorVersion = "1.6.7"
val logbackVersion = "1.2.10"
val reactVersion = "17.0.2-pre.299-kotlin-1.6.10"
val kmongoVersion = "4.5.0"

plugins {
    kotlin("multiplatform")
	//application //to run JVM part server
    id("org.jetbrains.compose")
	kotlin("plugin.serialization") version "1.6.10"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {

        val jsMain by getting {
            dependencies {
                implementation(npm("highlight.js", "10.7.2"))
                implementation(compose.web.core)
                implementation(compose.runtime)
				
				implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$reactVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$reactVersion")
            }
        }
    }
}

// a temporary workaround for a bug in jsRun invocation - see https://youtrack.jetbrains.com/issue/KT-48273
afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.9.0"
    }
}
