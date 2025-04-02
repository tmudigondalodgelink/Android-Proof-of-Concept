package com.example.domainmodule
import androidx.lifecycle.Observer
import com.example.presentationmodule.search.ISearchViewModel
import com.example.presentationmodule.search.SearchViewModel
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchViewModelTest: BaseTest() {
    private val sut: ISearchViewModel = SearchViewModel()

    @Test
    fun `increaseCount should increment count value`() {
        val observer = mockk<Observer<Int>>(relaxed = true)
        sut.count.observeForever(observer)

        assertEquals(0, sut.count.value)

        sut.increaseCount()
        assertEquals(1, sut.count.value)

        sut.increaseCount()
        assertEquals(2, sut.count.value)

        sut.count.removeObserver(observer)
    }
}
