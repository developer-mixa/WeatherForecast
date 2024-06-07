package com.example.weatherforecast.data.sources

import com.example.weatherforecast.data.BackendException
import com.example.weatherforecast.data.ConnectionException
import com.example.weatherforecast.data.ParseJsonException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

open class BaseRetrofitSource(moshi: Moshi) {

    private val errorAdapter = moshi.adapter(ErrorResponseBody::class.java)

    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: JsonDataException) {
            throw ParseJsonException(e)
        } catch (e: JsonEncodingException) {
            throw ParseJsonException(e)
        } catch (e: HttpException) {
            throw createServerException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun createServerException(e: HttpException): Exception {
        return try {
            val errorBody: ErrorResponseBody = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )!!
            BackendException(errorBody.error)
        } catch (e: Exception) {
            println(e)
            throw ParseJsonException(e)
        }
    }

    class ErrorResponseBody(
        val error: String
    )
}

