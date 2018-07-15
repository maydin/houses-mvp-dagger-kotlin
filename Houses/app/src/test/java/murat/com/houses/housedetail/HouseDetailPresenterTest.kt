package murat.com.houses.housedetail

import murat.com.houses.data.model.House
import murat.com.houses.data.source.DataRepository
import murat.com.houses.houselist.HouseListPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations



/**
 * Unit tests for the implementation of [HouseListPresenter]
 */
class HouseDetailPresenterTest {

    private val URL:String = "url1"

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var houseDetail: HouseDetailContract.View

    private lateinit var houseDetailPresenter: HouseDetailPresenter

    private lateinit var house: House

    @Before
    fun setupHouseListPresenter() {
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        houseDetailPresenter = HouseDetailPresenter(dataRepository, houseDetail,URL)

        house = House("url1", "name1", "region1","coat1","",null,null,null,null,"",null,null,null,null,null,null)
    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        houseDetailPresenter = HouseDetailPresenter( dataRepository, houseDetail,URL)

        // Then the presenter is set to the view
        verify(houseDetail).presenter = houseDetailPresenter
    }

    @Test
    fun presenterShouldShowHouseDetail(){
        Mockito.`when`(dataRepository.getHouse(URL)).thenReturn(house)
        // Get a reference to the class under test
        houseDetailPresenter = spy(HouseDetailPresenter( dataRepository, houseDetail,URL))
        houseDetailPresenter.start()

        verify(dataRepository).getHouse(URL)
        verify(houseDetailPresenter).showHouse()
        verify(houseDetail).showHouse(house)
    }

    @Test
    fun presenterShouldShowNoHouseDetail(){
        Mockito.`when`(dataRepository.getHouse(URL)).thenReturn(null)
        // Get a reference to the class under test
        houseDetailPresenter = spy(HouseDetailPresenter( dataRepository, houseDetail,URL))
        houseDetailPresenter.start()

        verify(dataRepository).getHouse(URL)
        verify(houseDetailPresenter).showHouse()
        verify(houseDetail).houseNotFound()
    }
}
