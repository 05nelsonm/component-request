# CHANGELOG

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
