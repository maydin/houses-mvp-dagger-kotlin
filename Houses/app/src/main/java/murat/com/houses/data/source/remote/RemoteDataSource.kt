package murat.com.houses.data.source.remote

import murat.com.houses.BuildConfig
import murat.com.houses.data.model.House
import murat.com.houses.data.source.DataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Remote data source that fetches data from a remote api
 */
@Singleton
class RemoteDataSource @Inject
constructor() : DataSource {

    internal var apiService: RemoteApiService

    init {

        /* TODO create an OkHttpClient and add caching interceptors
         * This will prevent unnecessary calls and will be useful when offline  */
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        apiService = retrofit.create(RemoteApiService::class.java)
    }

    /**
     * Fetches Houses
     *
     * @param callback callback for the result of the api query
     */
    override fun getHouses(callback: DataSource.LoadHousesCallback) {

        val houses = apiService.houses
        houses.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                val houseList = response.body()
                if (houseList == null || houseList.isEmpty())
                    callback.onDataNotAvailable()
                else
                    callback.onHousesLoaded(houseList)
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                callback.onError()
            }
        })

    }
}
