package murat.com.houses.data.source

import murat.com.houses.data.model.House
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRemoteDataSource @Inject
constructor() : DataSource {
    override fun getHouses(callback: DataSource.LoadHousesCallback) {
        callback.onHousesLoaded(HOUSES)
    }

    companion object {
         val HOUSES = arrayListOf(House("url1", "name1", "region1","coat1","",null,null,null,null,"",null,null,null,null,null,null),
                House("url2", "name2", "region2","coat2","",null,null,null,null,"",null,null,null,null,null,null))
    }

}