package com.example.movo.utils

import android.net.http.UrlRequest.Status

data class DataStatus<out T>(val status : Status,val data : T? = null,val message : String? = null){
    enum class Status{
        LOADING,
        SUCCESS,
        ERROR
    }

    companion object{
        fun <T> loading():DataStatus<T>{
            return DataStatus(Status.LOADING)
        }

        fun <T>success(data : T?) : DataStatus<T>{
            return DataStatus(Status.SUCCESS , data)
        }

        fun <T> error(message: String) : DataStatus<T>{
            return DataStatus(Status.ERROR, message = message)
        }

    }

}