rootProject.name = "component-request"

includeBuild("kotlin-components/includeBuild/dependencies")
includeBuild("kotlin-components/includeBuild/kmp")

@Suppress("PrivatePropertyName")
private val KMP_TARGETS: String? by settings
@Suppress("PrivatePropertyName")
private val CHECK_PUBLICATION: String? by settings
@Suppress("PrivatePropertyName")
private val KMP_TARGETS_ALL = System.getProperty("KMP_TARGETS_ALL") != null
@Suppress("PrivatePropertyName")
private val TARGETS = KMP_TARGETS?.split(',')

if (CHECK_PUBLICATION != null) {
    include(":tools:check-publication")
} else {
    include(":request-concept")
    include(":request-feature")
    include(":extensions:request-extension-navigation")
    include(":extensions:request-extension-navigation-androidx")

    include(":samples:activity")
    include(":samples:screens:screen-a")
    include(":samples:screens:screen-b")
    include(":samples:screens:screen-c")

    if (KMP_TARGETS_ALL || (TARGETS?.contains("ANDROID") != false && TARGETS?.contains("JVM") != false)) {
        include(":samples:app-android")
    }
}
