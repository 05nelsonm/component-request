# component-request

Agnostic and modularized framework for facilitating multi-module requests
whereby module decoupling can be maximized. See the sample android project
where it is used for navigation.

A full list of `kotlin-components` projects can be found [HERE](https://kotlin-components.matthewnelson.io)

### Get Started

```kotlin
// build.gradle.kts
dependencies {
    val requestVersion = "3.0.0"

    // `request-feature` will provide `request-concept`
    implementation("io.matthewnelson.kotlin-components:request-feature:$requestVersion")

    // If your project is modularized and/or you only need the abstractions
    implementation("io.matthewnelson.kotlin-components:request-concept:$requestVersion")


    // EXTENSIONS - navigation

    // navigation extension (provides `request-concept`)
    implementation("io.matthewnelson.kotlin-components:request-extension-navigation:$requestVersion")

    // alternatively, use the androidx navigation library extension (provides
    // `request-extension-navigation`). (android target only)
    implementation("io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$requestVersion")
}
```

```groovy
// build.gradle
dependencies {
    def requestVersion = "3.0.0"

    // `request-feature` will provide `request-concept`
    implementation "io.matthewnelson.kotlin-components:request-feature:$requestVersion"

    // If your project is modularized and/or you only need the abstractions
    implementation "io.matthewnelson.kotlin-components:request-concept:$requestVersion"


    // EXTENSIONS - navigation

    // navigation extension (provides `request-concept`)
    implementation "io.matthewnelson.kotlin-components:request-extension-navigation:$requestVersion"

    // alternatively, use the androidx navigation library extension (provides
    // `request-extension-navigation`). (android target only)
    implementation "io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$requestVersion"
}
```

### Kotlin Version Compatibility

**Note:** as of `3.0.0`, the experimental memory model for Kotlin Native is enabled.

|    request    |     kotlin     | kotlinx-coroutines |
| :-----------: | :------------: | :----------------: |
|     2.0.0     |     1.5.31     |       1.5.2        |
|     3.0.0     |     1.6.10     |       1.6.0        |

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
