# component-request
[![Kotlin](https://img.shields.io/badge/kotlin-1.6.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Kotlin Coroutines](https://img.shields.io/badge/coroutines-1.6.3-blue.svg?logo=kotlin)](https://github.com/Kotlin/kotlinx.coroutines)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)  

![android](https://camo.githubusercontent.com/b1d9ad56ab51c4ad1417e9a5ad2a8fe63bcc4755e584ec7defef83755c23f923/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d616e64726f69642d3645444238442e7376673f7374796c653d666c6174)
![ios](https://camo.githubusercontent.com/1fec6f0d044c5e1d73656bfceed9a78fd4121b17e82a2705d2a47f6fd1f0e3e5/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d696f732d4344434443442e7376673f7374796c653d666c6174)
![apple-silicon](https://camo.githubusercontent.com/a92c841ffd377756a144d5723ff04ecec886953d40ac03baa738590514714921/687474703a2f2f696d672e736869656c64732e696f2f62616467652f737570706f72742d2535424170706c6553696c69636f6e2535442d3433424246462e7376673f7374796c653d666c6174)
![jvm](https://camo.githubusercontent.com/700f5dcd442fd835875568c038ae5cd53518c80ae5a0cf12c7c5cf4743b5225b/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d6a766d2d4442343133442e7376673f7374796c653d666c6174)
![js](https://camo.githubusercontent.com/3e0a143e39915184b54b60a2ecedec75e801f396d34b5b366c94ec3604f7e6bd/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d6a732d4638444235442e7376673f7374796c653d666c6174)
![node-js](https://camo.githubusercontent.com/d08fda729ceebcae0f23c83499ca8f06105350f037661ac9a4cc7f58edfdbca9/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d6e6f64656a732d3638613036332e7376673f7374796c653d666c6174)
![linux](https://camo.githubusercontent.com/a2c518ecf30b2c88dd6af8bbc5281b6014686b916368e6197ef2a5e1dda7adb4/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d6c696e75782d3244334636432e7376673f7374796c653d666c6174)
![macos](https://camo.githubusercontent.com/1b8313498db244646b38a4480186ae2b25464e5e8d71a1920c52b2be5212b909/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d6d61636f732d3131313131312e7376673f7374796c653d666c6174)
![tvos](https://camo.githubusercontent.com/4ac08d7fb1bcb8ef26388cd2bf53b49626e1ab7cbda581162a946dd43e6a2726/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d74766f732d3830383038302e7376673f7374796c653d666c6174)
![watchos](https://camo.githubusercontent.com/135dbadae40f9cabe7a3a040f9380fb485cff36c90909f3c1ae36b81c304426b/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d77617463686f732d4330433043302e7376673f7374796c653d666c6174)
![windows](https://camo.githubusercontent.com/01bd13daf3ea3068952f50840e3f36a305803cc248af08f084cb9e37df78123d/687474703a2f2f696d672e736869656c64732e696f2f62616467652f706c6174666f726d2d77696e646f77732d3444373643442e7376673f7374796c653d666c6174)  


Agnostic and modularized framework for facilitating multi-module requests
whereby module decoupling can be maximized. See the sample android project
where it is used for navigation.

A full list of `kotlin-components` projects can be found [HERE](https://kotlin-components.matthewnelson.io)

### Get Started

```kotlin
// build.gradle.kts
dependencies {
    val vRequest = "3.0.4"

    // `request-feature` will provide `request-concept`
    implementation("io.matthewnelson.kotlin-components:request-feature:$vRequest")

    // If your project is modularized and/or you only need the abstractions
    implementation("io.matthewnelson.kotlin-components:request-concept:$vRequest")


    // EXTENSIONS - navigation

    // navigation extension (provides `request-concept`)
    implementation("io.matthewnelson.kotlin-components:request-extension-navigation:$vRequest")

    // alternatively, use the androidx navigation library extension (provides
    // `request-extension-navigation`). (android target only)
    implementation("io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$vRequest")
}
```

```groovy
// build.gradle
dependencies {
    def vRequest = "3.0.4"

    // `request-feature` will provide `request-concept`
    implementation "io.matthewnelson.kotlin-components:request-feature:$vRequest"

    // If your project is modularized and/or you only need the abstractions
    implementation "io.matthewnelson.kotlin-components:request-concept:$vRequest"


    // EXTENSIONS - navigation

    // navigation extension (provides `request-concept`)
    implementation "io.matthewnelson.kotlin-components:request-extension-navigation:$vRequest"

    // alternatively, use the androidx navigation library extension (provides
    // `request-extension-navigation`). (android target only)
    implementation "io.matthewnelson.kotlin-components:request-extension-navigation-androidx:$vRequest"
}
```

### Kotlin Version Compatibility

**Note:** as of `3.0.0`, the experimental memory model for Kotlin Native is enabled.

|    request    |     kotlin     | kotlinx-coroutines |
| :-----------: | :------------: | :----------------: |
|     3.0.4     |     1.6.21     |       1.6.3        |
|     3.0.3     |     1.6.21     |       1.6.1        |
|     3.0.2     |     1.6.21     |       1.6.1        |
|     3.0.1     |     1.6.10     |       1.6.0        |
|     3.0.0     |     1.6.10     |       1.6.0        |
|     2.0.0     |     1.5.31     |       1.5.2        |

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
