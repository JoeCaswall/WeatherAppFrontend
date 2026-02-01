package com.mobileappsfrontend.weatherapp
//
//import com.mobileappsfrontend.weatherapp.data.model.CurrentWeatherResponse
//import com.mobileappsfrontend.weatherapp.data.model.DefaultLocationResponse
//import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
//import com.mobileappsfrontend.weatherapp.ui.home.HomeViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.whenever
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class HomeViewModelTest {
//    private val testDispatcher = TestCoroutineDispatcher()
//    private lateinit var repository: WeatherRepository
//    private lateinit var viewModel: HomeViewModel
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//        repository = mock()
//        viewModel = HomeViewModel(repository)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//
//    @Test
//    fun `offline-first loads cached weather if available`() {
//        val cachedWeather = CurrentWeatherResponse(
//            cityName = "London",
//            temp = 20.0,
//            feelsLikeTemp = 19.0,
//            windDirection = "N",
//            windSpeedKmh = 10.0,
//            conditions = mock(),
//            airQuality = 50,
//            sunriseTime = "06:00",
//            sunsetTime = "18:00",
//            humidity = 60,
//            precipitation = 0.0
//        )
//        whenever(repository.getCachedWeather()).thenReturn(cachedWeather)
//        whenever(repository.getDefaultLocation()).thenThrow(RuntimeException("No network"))
//        viewModel.loadWeatherForDefaultLocation()
//        testDispatcher.advanceUntilIdle()
//        assertEquals(cachedWeather, viewModel.uiState)
//        assertNull(viewModel.errorMessage)
//    }
//
//    @Test
//    fun `offline-first loads fresh weather if network available`() {
//        val cachedWeather = CurrentWeatherResponse(
//            cityName = "London",
//            temp = 20.0,
//            feelsLikeTemp = 19.0,
//            windDirection = "N",
//            windSpeedKmh = 10.0,
//            conditions = mock(),
//            airQuality = 50,
//            sunriseTime = "06:00",
//            sunsetTime = "18:00",
//            humidity = 60,
//            precipitation = 0.0
//        )
//        val location = DefaultLocationResponse("London", 51.5, -0.1)
//        val freshWeather = cachedWeather.copy(temp = 21.0)
//        whenever(repository.getCachedWeather()).thenReturn(cachedWeather)
//        whenever(repository.getDefaultLocation()).thenReturn(location)
//        whenever(repository.getCurrentWeather(location.cityName, location.latitude, location.longitude)).thenReturn(freshWeather)
//        viewModel.loadWeatherForDefaultLocation()
//        testDispatcher.advanceUntilIdle()
//        assertEquals(freshWeather, viewModel.uiState)
//        assertNull(viewModel.errorMessage)
//    }
//
//    @Test
//    fun `offline-first shows error if no cache and no network`() {
//        whenever(repository.getCachedWeather()).thenReturn(null)
//        whenever(repository.getDefaultLocation()).thenThrow(RuntimeException("No network"))
//        viewModel.loadWeatherForDefaultLocation()
//        testDispatcher.advanceUntilIdle()
//        assertNull(viewModel.uiState)
//        assertNotNull(viewModel.errorMessage)
//    }
//}
