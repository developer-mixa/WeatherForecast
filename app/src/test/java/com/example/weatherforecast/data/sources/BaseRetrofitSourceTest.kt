package com.example.weatherforecast.data.sources

import com.example.weatherforecast.catch
import com.example.weatherforecast.data.ConnectionException
import com.example.weatherforecast.data.ParseJsonException
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class BaseRetrofitSourceTest : BaseRetrofitSource(
    mockk(relaxed = true)
) {

    @Test
    fun testWrapRetrofitExceptionThrowsParseJsonException() = runTest {
        catch<ParseJsonException> {
            wrapRetrofitExceptions {
                throw JsonDataException()
            }
        }
    }

    @Test
    fun testWrapRetrofitExceptionThrowsParseJsonExceptionWithJsonEncodingException() = runTest {
        catch<ParseJsonException> {
            wrapRetrofitExceptions {
                throw JsonEncodingException(null)
            }
        }
    }

    @Test
    fun testWrapRetrofitExceptionThrowsConnectionException() = runTest {
        catch<ConnectionException> {
            wrapRetrofitExceptions {
                throw IOException()
            }
        }
    }

    @Test
    fun testWrapRetrofitExceptionCreateBackendExceptionWhichThrowParseJsonException() = runTest {
        catch<ParseJsonException> {
            wrapRetrofitExceptions {
                throw HttpException(
                    Response.error<Any>(
                        500, ResponseBody.create(null, "")
                    )
                )
            }
        }
    }

    @Test
    fun testWrapRetrofitExceptionThrowException() = runTest {
        catch<Exception> {
            wrapRetrofitExceptions {
                throw Exception()
            }
        }
    }

}