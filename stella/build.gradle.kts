plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

var openCVSdkPath = projectDir.absolutePath + "/sdk/native/jni"

android {
    namespace = "org.jason.testapp.android.stella"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        externalNativeBuild {
            cmake {
                abiFilters += "arm64-v8a"
                arguments += listOf("-DANDROID_STL=c++_shared",
                "-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON",
                "-DOpenCV_DIR=${openCVSdkPath}",
                "-DANDROID_ARM_NEON=TRUE",
                "-DG2O_USE_CHOLMOD=OFF",
                "-DEIGEN_TEST_CXX11=ON",
                "-DBUILD_TESTING=OFF",
                "--log-level=VERBOSE"
                )
                cppFlags += listOf("-O3", "-DNDEBUG")
                cFlags += listOf("-O3", "-DNDEBUG")
            }
        }

        ndk {
            abiFilters.add("arm64-v8a")
        }
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    buildTypes {
        debug {
            packaging {
                jniLibs {
                    // uncomment the following line to keep debug symbols only if you need to
                    // debug native code
                    // keepDebugSymbols.add("**/*.so")
                    pickFirsts += "**/*.so"
                }
            }
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            packaging {
                jniLibs {
                    pickFirsts += "**/*.so"
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        aidl = true
        viewBinding = true
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}