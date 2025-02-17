package com.vks.repository.resource

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val networkError: NetworkError? = null,
    val code: Int? = null
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(
            data: T?,
            msg: String? = null,
            networkError: NetworkError? = NetworkError.UNKNOWN
        ): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                networkError
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> offline(data: T?): Resource<T> {
            return Resource(
                Status.OFFLINE,
                data,
                null
            )
        }
    }
}

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    SUCCESS,
    ERROR,
    OFFLINE,
    LOADING,
    SESSION_EXPIRE,
    SUSPENDED
}

fun getError(code: Int, message: String) = when (code) {
    420 -> Resource(Status.SUSPENDED, null, message)
    403 -> Resource(Status.SESSION_EXPIRE, null, message)
    409 -> Resource(Status.ERROR, null, message, code = code)
    else -> Resource.error(null, message)
}

enum class NetworkError {
    NO_CONNECTIVITY,
    TIMEOUT,
    INVALID_SERVER_URL,
    UNKNOWN
}
