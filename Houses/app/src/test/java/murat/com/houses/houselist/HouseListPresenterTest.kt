package murat.com.houses.houselist

import murat.com.houses.data.model.House
import murat.com.houses.data.source.DataRepository
import murat.com.houses.data.source.DataSource
import murat.com.houses.data.source.argumentCaptor
import murat.com.houses.data.source.capture
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.verify



/**
 * Unit tests for the implementation of [HouseListPresenter]
 */
class HouseListPresenterTest {

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var housesView: HouseListContract.View

    @Captor
    private lateinit var loadHousesCallbackCaptor: ArgumentCaptor<DataSource.LoadHousesCallback>

    private lateinit var houseListPresenter: HouseListPresenter

    private lateinit var houses: MutableList<House>

    @Before
    fun setupHouseListPresenter() {
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        houseListPresenter = HouseListPresenter(dataRepository, housesView)

        houses = arrayListOf(House("url1", "name1", "region1","coat1","",null,null,null,null,"",null,null,null,null,null,null),
                House("url2", "name2", "region2","coat2","",null,null,null,null,"",null,null,null,null,null,null))
    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        houseListPresenter = HouseListPresenter( dataRepository, housesView)

        // Then the presenter is set to the view
        verify(housesView).presenter = houseListPresenter
    }

    @Test
    fun loadHousesFromRepositoryAndLoadIntoView() {
        with(houseListPresenter) {
            loadHouses(true,true)
        }
        // Callback is captured and invoked with stubbed houses
        verify(dataRepository).getHouses(capture(loadHousesCallbackCaptor))
        loadHousesCallbackCaptor.value.onHousesLoaded(houses)

        // Then progress indicator is shown
        val inOrder = Mockito.inOrder(housesView)
        inOrder.verify(housesView).setLoadingIndicator(true)
        // Then progress indicator is hidden and all houses are shown in UI
        inOrder.verify(housesView).setLoadingIndicator(false)
        val showHousesArgumentCaptor = argumentCaptor<List<House>>()
        verify(housesView).showHouses(capture(showHousesArgumentCaptor))
        Assert.assertTrue(showHousesArgumentCaptor.value.size == 2)
    }

    @Test
    fun onDataNotAvailable_ShowsDataNotAvailable() {
        with(houseListPresenter) {
            // When houses are loaded
            loadHouses(true,true)
        }
        // And the houses aren't available in the repository
        verify(dataRepository).getHouses(capture(loadHousesCallbackCaptor))
        loadHousesCallbackCaptor.value.onDataNotAvailable()

        // Then no houses view is displayed
        verify(housesView).showNoHouses()
    }

    @Test
    fun onError_ShowsError() {
        with(houseListPresenter) {
            // When houses are loaded
            loadHouses(true,true)
        }

        // And the houses aren't available in the repository
        verify(dataRepository).getHouses(capture(loadHousesCallbackCaptor))
        loadHousesCallbackCaptor.value.onError()

        // Then an error message is shown
        verify(housesView).showLoadingHousesError()
    }
}
