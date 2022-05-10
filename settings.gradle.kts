rootProject.name = "component-request"

includeBuild("kotlin-components/includeBuild/dependencies")
includeBuild("kotlin-components/includeBuild/kmp")

include(":request-concept")
include(":request-feature")
include(":extensions:request-extension-navigation")
include(":extensions:request-extension-navigation-androidx")

include(":samples:activity")
include(":samples:screens:screen-a")
include(":samples:screens:screen-b")
include(":samples:screens:screen-c")

// if ANDROID is not being built, don't include the app as it relies
// on some android only kmp projects
@Suppress("PrivatePropertyName")
private val KMP_TARGETS: String? by settings

private val allTargets = System.getProperty("KMP_TARGETS_ALL") != null
private val targets = KMP_TARGETS?.split(',')

if (allTargets || targets?.contains("ANDROID") != false) {
    include(":samples:app-android")
}

@Suppress("PrivatePropertyName")
private val CHECK_PUBLICATION: String? by settings
if (CHECK_PUBLICATION != null) {
    include(":tools:check-publication")
}
