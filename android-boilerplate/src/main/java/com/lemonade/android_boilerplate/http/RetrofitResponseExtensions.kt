package com.lemonade.android_boilerplate.http
import retrofit2.HttpException
import retrofit2.Response
import kotlin.jvm.Throws

const val START_OF_ERRORS_STATUS_CODE = 400

/**
 * isOK is a extension function for the Response class. The main goal of this function is check
 * result of response.
 *
 * @return True if response is OK, false otherwise.
 */
fun <T: Any> Response<T>.isOK(): Boolean {
    return body() != null && isSuccessful
}

/**
 * onSuccess is a extension function for the Response class. The main goal of this function is turn
 * response data into a specific data structure.
 *
 * @param block A callback with T (data structure) as a parameter that returns nothing.
 * @return The original Response object.
 */
inline fun <T: Any> Response<T>.onSuccess(block: (value: T) -> Unit): Response<T> {
    if(isOK()) block(body() as T)
    return this
}

/**
 * onFailure is a extension function for the Response class. The main goal of this function is turn
 * response data into HttpException data structure without try/catch.
 *
 * @param block A callback with HttpException as a parameter that returns nothing.
 * @return The original Response object.
 */
inline fun <T:Any> Response<T>.onFailure(block: (error: HttpException) -> Unit): Response<T> {
    val code = code()
    if(code >= START_OF_ERRORS_STATUS_CODE) {
        val error = HttpException(this)
        block(error)
    }
    return this
}

/**
 * getOrThrow is a extension function for the Response class. The main goal of this function is
 * to presenter a simple way to handle errors when call a retrofit function that returns a Response
 *
 * @param onFailure A callback with HttpException as a parameter that returns nothing.
 * @return T (data structure) if response is OK, otherwise throw HttpException.
 */
@Throws
inline fun <T:Any> Response<T>.getOrThrow(onFailure: (error: HttpException) -> Unit): T {
    this.onSuccess { return it }
    this.onFailure(onFailure)
    throw HttpException(this)
}