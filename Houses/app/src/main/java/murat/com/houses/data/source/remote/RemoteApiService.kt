package murat.com.houses.data.source.remote

import murat.com.houses.data.model.House
import retrofit2.Call
import retrofit2.http.GET

/**
 * Retrofit API interface
 */

interface RemoteApiService {

    @get:GET("houses/")
    val houses: Call<List<House>>
}
