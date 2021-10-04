package io.matthewnelson.component.request.master.drivers

import io.matthewnelson.component.request.master.util.RequestHolder
import io.matthewnelson.component.request.master.util.RequestId
import io.matthewnelson.component.request.slave.Request
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Caches submitted requests such that upon collection, all requests (up to the [replayCacheSize]
 * specified) are replayed. Already executed requests are tracked by their [RequestId], such that
 * [CachedRequestDriver.executeRequest] will return `false` if it has already been executed; this is
 * necessary for platforms such as Android, where configuration changes could interrupt submission
 * and/or collection.
 *
 * As a cache is being utilized, be mindful of context/callbacks whereby passing of it in the [Request]'s
 * constructor could cause a leak.
 *
 * Filter requests via the [whenTrueExecuteRequest] callback upon instantiation of [CachedRequestDriver].
 *
 * [replayCacheSize] must be > 0
 * */
open class CachedRequestDriver<T: Any>(
    // For android, 3 is a good value. This really depends on if you have navigation being
    // executed automatically w/o user input (say, after animation completes). This is due
    // to configuration changes which make tracking what requests have been executed a necessity.
    val replayCacheSize: Int,
    private val whenTrueExecuteRequest: (suspend (instance: T, request: Request<T>) -> Boolean)? = null
): RequestDriver<T>() {

    init {
        require(replayCacheSize > 0) {
            "CachedRequestDriver.replayCacheSize must be greater than 0"
        }
    }

    private val executedRequestsLock = Mutex()
    private val executedRequests: MutableList<RequestId> = ArrayList(replayCacheSize)

    /**
     * Returns true if the request was executed, and false if it was not
     * */
    override suspend fun executeRequest(instance: T, holder: RequestHolder<T>): Boolean {
        executedRequestsLock.withLock {
            if (executedRequests.contains(holder.getId())) {
                return false
            }


            if (whenTrueExecuteRequest?.invoke(instance, holder.request) == false) {
                return false
            }

            return if (currentCoroutineContext().isActive) {
                if (executedRequests.size == replayCacheSize) {
                    executedRequests.removeFirst()
                }
                executedRequests.add(holder.getId())

                holder.request.execute(instance)

                true
            } else {
                false
            }
        }
    }


    @Suppress("RemoveExplicitTypeArguments")
    private val _requestSharedFlow: MutableSharedFlow<RequestHolder<T>> by lazy {
        MutableSharedFlow<RequestHolder<T>>(replayCacheSize)
    }

    override suspend fun collect(action: suspend (value: RequestHolder<T>) -> Unit) {
        _requestSharedFlow.asSharedFlow().collect { action.invoke(it) }
    }

    /**
     * Assigns a [RequestId] to the navigation request such that execution of it
     * can be tracked.
     * */
    override suspend fun submitRequest(request: Request<T>) {
        _requestSharedFlow.emit(RequestHolder(request))
    }
}
