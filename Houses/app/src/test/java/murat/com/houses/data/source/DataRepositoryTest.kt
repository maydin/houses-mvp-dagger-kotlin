/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package murat.com.houses.data.source

import murat.com.houses.data.model.House
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the implementation of the in-memory repository with cache.
 */
class DataRepositoryTest {

    private lateinit var dataRepository: DataRepository

    private lateinit var remoteDataSource: TestRemoteDataSource

    @Mock
    private lateinit var loadHousesCallback: DataSource.LoadHousesCallback

    @Before
    fun setupHouseRepository() {
        MockitoAnnotations.initMocks(this)
        remoteDataSource = spy(TestRemoteDataSource())
        // Get a reference to the class under test
        dataRepository = spy(DataRepository(remoteDataSource))
    }

    @Test
    fun getHousesWithDirtyCache_HousesAreRetrievedFromRemote() {
        // When calling getHouses in the repository with dirty cache
        dataRepository.refreshHouses()
        dataRepository.getHouses(loadHousesCallback)

        // Verify the houses from the remote data source are returned, not the cache
        verify(remoteDataSource).getHouses(any())
        verify(loadHousesCallback).onHousesLoaded(HOUSES)
    }

    @Test
    fun getHousesWithCleanCache_HousesAreRetrievedFromCache(){
        dataRepository.getHouses(loadHousesCallback)
        // Verify the houses from the remote data source are not returned, but the cache
        verify(remoteDataSource, never()).getHouses(loadHousesCallback)
        verify(loadHousesCallback).onHousesLoaded(HOUSES)
    }

    @Test
    fun getHouses_refreshesCache() {
        // Mark cache as dirty to force a reload of data from remote data source.
        dataRepository.refreshHouses()

        // When calling getHouses in the repository
        dataRepository.getHouses(loadHousesCallback)

        // Verify that the data fetched from the remote data source was saved in cache.
        verify(dataRepository).refreshCache(HOUSES)
    }

    companion object {

        private val HOUSES = arrayListOf(House("url1", "name1", "region1","coat1","",null,null,null,null,"",null,null,null,null,null,null),
                House("url2", "name2", "region2","coat2","",null,null,null,null,"",null,null,null,null,null,null))
    }

}
