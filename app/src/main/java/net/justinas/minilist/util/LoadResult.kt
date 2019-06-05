package net.justinas.minilist.util

sealed class LoadResult<out R> {

    data class Success<out T>(val data: T) : LoadResult<T>()
    data class Error(val exception: Exception) : LoadResult<Nothing>() {
        constructor(trowable: Throwable): this(java.lang.Exception(trowable))
    }
    object Loading : LoadResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val LoadResult<*>.succeeded get() = this is LoadResult.Success && data != null


