package com.example.unsplashapicompose.data

import org.json.JSONObject
import retrofit2.Response

open class BaseRepository {

    fun <T> parseApiResult(response: Response<T>): Result<T> {
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return try {
                val jsonObject = JSONObject(response.errorBody()!!.string())
                val message = jsonObject.optString("message")
                val messagesJsonArray = jsonObject.optJSONArray("message")
                if (messagesJsonArray != null) {
                    Result.failure(Exception(messagesJsonArray.toString()))
                } else {
                    Result.failure(Exception(message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}