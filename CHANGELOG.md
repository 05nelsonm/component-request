# CHANGELOG

## Version 3.0.2 (2022-05-08)
 - Updates Kotlin-Components
     - Bumps `kotlin` from `1.6.10` -> `1.6.21`
     - Bumps `coroutines` from `1.6.0` -> `1.6.1`
     - Bumps `androidx.navigation` from `2.4.0` -> `2.4.2`

## Version 3.0.1 (2022-02-05)
 - Adds `ensureSingleExecution` argument to navigation-androidx extension's `PopBackStack` class
 - Adds `JvmOverloads` annotations
 - Bumps `androidx.navigation` from `2.3.5` -> `2.4.0`
 - Codebase cleanup

## Version 3.0.0 (2022-01-14)
 - Bumps Dependencies
 - Enables Kotlin Native's new Memory Model
 - Adds Kotlin Version compatibility matrix documentation
 - Removes confusing concept/feature versioning
 - Removes unnecessary expect/actual class declarations
 - Fixes `RandomId`'s implementation to be more performant
 - Migrates tests from Jvm -> Common code (thanks kotlinx-coroutines-test!)
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
