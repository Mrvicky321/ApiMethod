package com.example.apimethod

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    @GET("todos")
    fun getToDoList(): Observable<List<TodoData>>


    @POST("todos")
    fun postToDoList(@Body todoModel: TodoData): Observable<TodoData>

    @PUT("todos/{id}")
    fun putToDoList(@Body todoModel: TodoData,@Path("id") id: Int): Observable<TodoData>

    @PATCH("todos/{id}")
    fun patchToDoList(@Body todoData: TodoData, @Path("id") id: Int): Observable<TodoData>

    @DELETE("posts/{id}")
    fun deletepost(@Path("id") id: Int): Observable<Any>




}