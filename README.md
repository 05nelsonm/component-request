# component-request

Agnostic and modularized framework for facilitating multi-module requests
whereby module decoupling can be maximized. See the sample android project
where it is used for navigation.

A full list of `kotlin-components` projects can be found [HERE](https://kotlin-components.matthewnelson.io)

<!-- TODO: Link to concept/feature modularization gist (have to write first) -->
Publications utilize concept/feature modularization such that `concept`s will only increment
on major publication releases (ie. from `2` -> `3`) and `feature`s will increment on minor
publication releases (ie. from `2.0.0` -> `2.1.0`), leaving modules relying on `concept`s 
undisturbed.

### Get Started

```kotlin
// build.gradle.kts
dependencies {
    val (concept, feature) = Pair("2", "2.0.0")

    // `request-feature` dependency will automatically import the `request-concept` dependency (CommonMain Target)
    implementation("io.matthewnelson.kotlin-components:request-feature:$feature")

    // If your project is modularized and/or you only need the abstractions (CommonMain Target)
    implementation("io.matthewnelson.kotlin-components:request-concept:$concept")

    // navigation extension for the `request-concept` (CommonMain Target)
    implementation("io.matthewnelson.kotlin-components:request-extension-navigation:$concept")

    // androidx navigation implementation of the navigation extension (Android Target)
    implementation("io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$concept")
}
```

```groovy
// build.gradle
dependencies {
    def concept = "2"
    def feature = "$concept.0.0"
    
    // `request-feature` dependency will automatically import the `request-concept` dependency (CommonMain Target)
    implementation "io.matthewnelson.kotlin-components:request-feature:$feature"

    // If your project is modularized and/or you only need the abstractions (CommonMain Target)
    implementation "io.matthewnelson.kotlin-components:request-concept:$concept"

    // navigation extension for the `request-concept` (CommonMain Target)
    implementation "io.matthewnelson.kotlin-components:request-extension-navigation:$concept"
    
    // androidx navigation implementation of the navigation extension (Android Target)
    implementation "io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$concept"
}
```

Example Monolithic (single module) Android Project (non-kotlin multiplatform)
```kotlin
// App module build.gradle.kts
dependencies {
    val (concept, feature) = Pair("2", "2.0.0")
    implementation("io.matthewnelson.kotlin-components:request-feature:$feature")
    implementation("io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$concept")
}
```

Example Modularized Android Project (non-kotlin multiplatform)
```kotlin
// Screen-A (Fragment) module build.gradle.kts
dependencies {
    val concept = "2"
    // will automatically import `request-concept` and `request-extension-navigation` dependencies
    // and then provide them to the above `App, or Activity` module
    api("io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$concept")
}

// App, or Activity module build.gradle.kts
dependencies {
    implementation(project(":screens:screen-a"))

    val feature = "2.0.0"
    implementation("io.matthewnelson.kotlin-components:request-feature:$feature")
}
```

### Git

This project utilizes git submodules. You will need to initialize them when 
cloning the repository via:

```bash
$ git clone --recursive https://github.com/05nelsonm/component-request.git
```

If you've already cloned the repository, run:
```bash
$ git checkout master
$ git pull
$ git submodule update --init
```

In order to keep submodules updated when pulling the latest code, run:
```bash
$ git pull --recurse-submodules
```
