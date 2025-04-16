package com.example.myassssmentapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import com.example.myassssmentapplication.model.LoginResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val repository: ArtworkRepository = mock()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        println("Setting up test...")
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        println("Tearing down test...")
        Dispatchers.resetMain()
    }

    @Test
    fun `login success returns keypass`() = runTest {
        println("Running login success test...")
        
        // Given
        val username = "test"
        val password = "password"
        val keypass = "test-keypass"
        whenever(repository.login(username, password))
            .thenReturn(LoginResponse(keypass))

        // When
        viewModel.login(username, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.loginResult.value
        println("Login result: $result")
        assertTrue("Expected Success result but was $result", result is LoginResult.Success)
        assertEquals("Expected keypass to match", keypass, (result as LoginResult.Success).keypass)
    }

    @Test
    fun `login failure returns error`() = runTest {
        println("Running login failure test...")
        
        // Given
        val username = "test"
        val password = "wrong"
        whenever(repository.login(username, password))
            .thenReturn(null)

        // When
        viewModel.login(username, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.loginResult.value
        println("Login result: $result")
        assertTrue("Expected Error result but was $result", result is LoginResult.Error)
    }
} 