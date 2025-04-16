package com.example.myassssmentapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myassssmentapplication.model.Artwork
import com.example.myassssmentapplication.repository.ArtworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val repository: ArtworkRepository = mock()
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        println("Setting up dashboard test...")
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        println("Tearing down dashboard test...")
        Dispatchers.resetMain()
    }

    @Test
    fun `loadArtworks success returns list of artworks`() = runTest {
        println("Running loadArtworks success test...")
        
        // Given
        val keypass = "test-keypass"
        val artworks = listOf(
            Artwork("Mona Lisa", "Leonardo da Vinci", "Oil paint", 1503, "Famous painting")
        )
        whenever(repository.getArtworks(keypass))
            .thenReturn(artworks)

        // When
        viewModel.loadArtworks(keypass)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.dashboardResult.value
        println("Dashboard result: $result")
        assertTrue("Expected Success result but was $result", result is DashboardResult.Success)
        assertEquals("Expected artworks to match", artworks, (result as DashboardResult.Success).artworks)
    }

    @Test
    fun `loadArtworks failure returns error`() = runTest {
        println("Running loadArtworks failure test...")
        
        // Given
        val keypass = "invalid-keypass"
        val errorMessage = "Network error"
        whenever(repository.getArtworks(keypass))
            .thenThrow(RuntimeException(errorMessage))

        // When
        viewModel.loadArtworks(keypass)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.dashboardResult.value
        println("Dashboard result: $result")
        assertTrue("Expected Error result but was $result", result is DashboardResult.Error)
        assertEquals("Expected error message to match", errorMessage, (result as DashboardResult.Error).message)
    }
} 