package com.owasp.top.mobile.demo.apis

import com.owasp.top.mobile.demo.data.ObjectCryptiiBase
import com.owasp.top.mobile.demo.data.ResponseBase
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OwaspSecureApi {
    @POST("secure/owasp-demo/api/v1/signIn")
    suspend fun login(
        @Header("api-key-x") apiKeyX: String,
        @Body request: ObjectCryptiiBase
    ): Response<ResponseBase<ObjectCryptiiBase>>

    @POST("secure/owasp-demo/api/v1/signUp")
    suspend fun signUp(
        @Header("api-key-x") apiKeyX: String,
        @Body request: ObjectCryptiiBase
    ): Response<ResponseBase<ObjectCryptiiBase>>

    /// Tareas
    @POST("secure/owasp-demo/api/v1/tasks")
    suspend fun createTasks(
        @Header("authorization") authorization: String,
        @Body request: ObjectCryptiiBase
    ): Response<ResponseBase<ObjectCryptiiBase>>

    @PUT("secure/owasp-demo/api/v1/tasks/{id}")
    suspend fun updateTasks(
        @Header("authorization") authorization: String,
        @Path("id") id: String,
        @Body request: ObjectCryptiiBase
    ): Response<ResponseBase<ObjectCryptiiBase>>

    @DELETE("secure/owasp-demo/api/v1/tasks/{id}")
    suspend fun deleteTasks(
        @Header("authorization") authorization: String,
        @Path("id") id: String
    ): Response<ResponseBase<ObjectCryptiiBase>>

    @GET("secure/owasp-demo/api/v1/tasks")
    suspend fun getTasks(
        @Header("authorization") authorization: String
    ): Response<ResponseBase<ObjectCryptiiBase>>

    /// Actividades
    @POST("secure/owasp-demo/api/v1/tasks/activities")
    suspend fun createActividades(
        @Header("authorization") authorization: String,
        @Body request: ObjectCryptiiBase
    ): Response<ResponseBase<ObjectCryptiiBase>>

    @PUT("secure/owasp-demo/api/v1/tasks/activities/{id}")
    suspend fun updateActivities(
        @Header("authorization") authorization: String,
        @Path("id") id: String,
        @Body request: ObjectCryptiiBase
    ): Response<ResponseBase<ObjectCryptiiBase>>

    @DELETE("secure/owasp-demo/api/v1/tasks/activities/{id}")
    suspend fun deleteActivities(
        @Header("authorization") authorization: String,
        @Path("id") id: String
    ): Response<ResponseBase<ObjectCryptiiBase>>

    @GET("secure/owasp-demo/api/v1/tasks/activities/{task_id}")
    suspend fun getActivities(
        @Header("authorization") authorization: String,
        @Path("task_id") username: String
    ): Response<ResponseBase<ObjectCryptiiBase>>

}