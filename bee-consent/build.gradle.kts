plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.benkkstudio.consent"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.benkkstudio"
            artifactId = "consent"
            version = "0.0.4"
            artifact("$buildDir/outputs/aar/bee-consent-release.aar")
        }
    }

    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/benkkstudios/BENKKSTUDIOCORE")
            credentials {
                username = "benkkstudios"
                password = "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"
            }
        }
    }
}

dependencies {
    api("com.google.android.ump:user-messaging-platform:2.2.0")
}