import io.matthewnelson.components.dependencies.Plugins

plugins {
    `kotlin-dsl`
    id("dependencies")
}

System.setProperty("GRADLE_ANDROID", Plugins.android.gradle)
System.setProperty("GRADLE_ANDROIDX_NAVIGATION_SAFEARGS", Plugins.androidx.navigation.safeArgs)
System.setProperty("GRADLE_GOOGLE_HILT", Plugins.google.hilt)
System.setProperty("GRADLE_KOTLIN", Plugins.kotlin.gradle)
System.setProperty("GRADLE_INTELLIJ", Plugins.intellijGradle)
System.setProperty("GRADLE_MAVEN_PUBLISH", Plugins.mavenPublish)

repositories {
    gradlePluginPortal()
}
