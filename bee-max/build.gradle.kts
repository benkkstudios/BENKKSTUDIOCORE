plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.benkkstudio.max"
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
            artifactId = "max"
            version = "0.0.2"
            artifact("$buildDir/outputs/aar/bee-max-release.aar")
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
    api(project(":bee-consent"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.applovin:applovin-sdk:12.1.0")
    implementation("com.google.code.gson:gson:2.10.1")

}