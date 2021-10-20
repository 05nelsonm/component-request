rootProject.name = "component-request"

includeBuild("kotlin-components/includeBuild/dependencies")
includeBuild("kotlin-components/includeBuild/kmp")

include(":request-feature")
include(":request-concept")

@Suppress("PrivatePropertyName")
private val KMP_TARGETS: String? by settings
if (KMP_TARGETS?.split(',')?.contains("ANDROID") != false) {
    include(":extensions:navigation-androidx")

    include(":samples:app-android")
    include(":samples:activity")
    include(":samples:screens:screen-a")
    include(":samples:screens:screen-b")
    include(":samples:screens:screen-c")
}
