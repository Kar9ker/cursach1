package com.example.myapplication10102024

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

data class UserObject(
    val name: String,
    val password: String
)

data class Medicine(
    val id: Int,
    val name: String,
    val description: String
)

data class UserMed(
    val id: Int,
    val user: Int,
    val medicine: Medicine
)

data class UserMedRequest(
    val user_name: String,
    val medicine_name: String
)


interface ApiService {
    @GET("/api/users/")
    suspend fun getAllObjects(): List<UserObject>

    @POST("/api/users/")
    suspend fun createObject(@Body newObject: UserObject): UserObject

    @GET("/api/users-meds/")
    suspend fun getUsersMeds(@Query("name") name: String): Response<List<UserMed>>

    @POST("/api/users-meds/")
    suspend fun addUserMed(@Body userMedRequest: UserMedRequest): Response<UserMed>

    @HTTP(method = "DELETE", path = "/api/users-meds/", hasBody = true)
    suspend fun deleteUserMed(@Body deleteRequest: UserMedRequest): Response<Unit>

    @GET("/api/medicines/")
    suspend fun getMedicines(): Response<List<Medicine>>
}
