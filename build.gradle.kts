import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        // System properties for plugin versions are set in buildSrc
        // in order to pass the "dependencies" plugins' values as
        // we cannot access them here until after the buildscript runs

        // Can't use latest AGP with Intellij IDEA yet
        // See: https://youtrack.jetbrains.com/issue/IDEA-264255
//        classpath("com.android.tools.build:gradle:4.2.0")
        classpath(System.getProperty("GRADLE_ANDROID"))

        classpath(System.getProperty("GRADLE_ANDROIDX_NAVIGATION_SAFEARGS"))
        classpath(System.getProperty("GRADLE_GOOGLE_HILT"))
        classpath(System.getProperty("GRADLE_KOTLIN"))
        classpath(System.getProperty("GRADLE_INTELLIJ"))
        classpath(System.getProperty("GRADLE_MAVEN_PUBLISH"))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply(from = rootProject.file("kotlin-components/gradle/maven-publish-env.gradle"))

allprojects {
    ext.set("VERSION_NAME", "1.0.0-SNAPSHOT")

    // The trailing 2 digits are for `alpha##` releases. For example:
    //     4.4.1-alpha02 = 441102 where `102` stands for alpha02
    //     4.4.1-beta01 = 441201 where `201` stands for beta01
    //     4.4.1-rc01 = 441301 where `301` stands for rc01
    ext.set("VERSION_CODE", 100000)

    ext.set("POM_INCEPTION_YEAR", 2021)

    ext.set("POM_URL", "https://github.com/05nelsonm/component-request")
    ext.set("POM_SCM_URL", "https://github.com/05nelsonm/component-request")
    ext.set("POM_SCM_CONNECTION", "scm:git:git://github.com/05nelsonm/component-request.git")
    ext.set("POM_SCM_DEV_CONNECTION", "scm:git:ssh://git@github.com/05nelsonm/component-request.git")

    group = property("GROUP") as String
    version = property("VERSION_NAME") as String
}

subprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    tasks.withType<Test> {
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events(STARTED, PASSED, SKIPPED, FAILED)
            showStandardStreams = true
        }
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
