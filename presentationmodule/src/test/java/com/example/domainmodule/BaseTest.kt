package com.example.domainmodule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    open fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}