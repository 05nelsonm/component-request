rootProject.name = "component-request"

includeBuild("kotlin-components/includeBuild/dependencies")
includeBuild("kotlin-components/includeBuild/kmp")

include(":request-concept")
include(":request-feature")
include(":extensions:navigation-androidx")

include(":samples:activity")
include(":samples:screens:screen-a")
include(":samples:screens:screen-b")
include(":samples:screens:screen-c")

// if ANDROID is not being built, don't include the app as it relies
// on some android only kmp projects
@Suppress("PrivatePropertyName")
private val KMP_TARGETS: String? by settings
@Suppress("PrivatePropertyName")
private val KMP_TARGETS_ALL: String? by settings
if (KMP_TARGETS_ALL != null || KMP_TARGETS?.split(',')?.contains("ANDROID") != false) {
    include(":samples:app-android")
}

@Suppress("PrivatePropertyName")
private val CHECK_PUBLICATION: String? by settings
if (CHECK_PUBLICATION != null) {
    include(":tools:check-publication")
}
