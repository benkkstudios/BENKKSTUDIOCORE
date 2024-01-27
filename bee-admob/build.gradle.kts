plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.benkkstudio.admob"
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
            artifactId = "admob"
            version = "0.0.4"
            artifact("$buildDir/outputs/aar/bee-admob-release.aar")
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
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    api("com.google.android.gms:play-services-ads:22.6.0")
    api("androidx.lifecycle:lifecycle-process:2.7.0")

}