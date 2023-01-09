# CHANGELOG

## Version 3.0.6 (2023-01-09)
 - Updates `kotlin-components` submodule
     - Kotlin `1.7.20` -> `1.8.0`

## Version 3.0.5 (2023-01-07)
 - Updates `kotlin-components` submodule
     - Kotlin `1.6.21` -> `1.7.20`
     - Coroutines `1.6.3` -> `1.6.4`
     - Android Gradle Plugin `7.0.4` -> `7.3.1`
     - androidx.navigation `2.4.2` -> `2.5.3`
     - androidx.lifecycle `2.4.1` -> `2.5.1`

## Version 3.0.4 (2022-06-24)
 - Updates `kotlin-components` submodule
     - Coroutines `1.6.1` -> `1.6.3`
 - Re-enable compiler flag `enableCompatibilityMetadataVariant=true` to support
   non-hierarchical projects. (sorry...)

## Version 3.0.3 (2022-05-14)
 - Updates `kotlin-components` submodule
     - Support new targets:
         - `iosArm32`
         - `iosSimulatorArm64`
         - `tvosSimulatorArm64`
         - `watchosx86`
         - `watchosSimulatorArm64`

## Version 3.0.2 (2022-05-08)
 - Updates `kotlin-components` submodule
     - Kotlin `1.6.10` -> `1.6.21`
     - Coroutines `1.6.0` -> `1.6.1`
     - androidx.navigation `2.4.0` -> `2.4.2`

## Version 3.0.1 (2022-02-05)
 - Adds `ensureSingleExecution` argument to navigation-androidx extension's `PopBackStack` class
 - Adds `JvmOverloads` annotations
 - Updates `kotlin-components` submodule
     - androidx.navigation `2.3.5` -> `2.4.0`
 - Codebase cleanup

## Version 3.0.0 (2022-01-14)
 - Updates `kotlin-components` submodule
     - Kotlin `1.5.31` -> `1.6.10`
     - Coroutines `1.5.2` -> `1.6.0`
     - androidx.navigation `2.3.5` -> `2.4.0`
 - Enables Kotlin Native's new Memory Model
 - Adds Kotlin Version compatibility matrix documentation
 - Removes confusing concept/feature versioning
 - Removes unnecessary expect/actual class declarations
 - Fixes `RandomId`'s implementation to be more performant
 - Migrate tests from Jvm -> Common code (thanks kotlinx-coroutines-test!)
 - Codebase cleanup

## Version concept(version = 2), feature(version = 2.0.0) (2021-10-29)
 - Adds ability to set Jvm target JavaVersion compile option (Java11 by default)
 - Updates publications for concept/feature compatibility
     - concept version 2
         - Publication of major version releases only
     - feature version 2.0.0
         - Publication of minor version will increment and depend on prior
           concept versions
    

## Version 1.0.0 (2021-10-24)
 - Initial Release
