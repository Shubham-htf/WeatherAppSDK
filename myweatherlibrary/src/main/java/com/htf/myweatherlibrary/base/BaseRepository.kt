package com.htf.myweatherlibrary.base


import android.content.Intent
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException




open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call:  () -> Response<T>): T? {
        var output: T? = null
        val result = sampleApiCall(call, "")
        output = when (result) {
            is Output.Success -> {
                result.output ?: "Success" as T
            }
            is Output.Error ->
                result.exception as T
        }
        return output
    }

    private suspend fun <T : Any> sampleApiCall(
        call: suspend () -> Response<T>,
        error: String? = null
    ): Output<T> {
        val response=call.invoke()
        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else {
            val message = errorResponse(response,call)
            Output.Error(IOException(message))
        }
    }

    private fun <T : Any> errorResponse(response: Response<T>,call: suspend () -> Response<T>): String? {
        val code = response.code()
        var message: String? = null

        when(code){
            401->{



            }
            403->{

            }

            422->{
                //Validation Errors

            }
            else->{


            }
        }

        return message
    }

}

