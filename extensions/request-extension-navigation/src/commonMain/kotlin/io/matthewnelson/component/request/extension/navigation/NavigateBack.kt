/*
*  Copyright 2021 Matthew Nelson
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
* */
package io.matthewnelson.component.request.extension.navigation

import io.matthewnelson.component.request.concept.Request

/**
 * A special [Request] for navigating back. Various platforms have different
 * implementations, but this special [Request] is used in the underlying [Navigator]
 * class when utilizing the navigation extension and defined in platform specific
 * navigation extensions.
 * */
abstract class NavigateBack<T: Any>: Request<T>()
