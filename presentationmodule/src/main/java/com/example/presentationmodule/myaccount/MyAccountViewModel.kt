package com.example.presentationmodule.myaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domainmodule.models.User
import com.example.domainmodule.usecases.IGetMeUseCase
import com.example.domainmodule.usecases.ISignOutUseCase
import com.example.domainmodule.utilities.FlowResult
import com.example.presentationmodule.bookings.collectInScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IMyAccountViewModel {
    val counter: StateFlow<Int>
    val user: StateFlow<User?>
    fun increment()
    fun getMe()
    fun signOut()
}

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val getMeUseCase: IGetMeUseCase,
    private val signOutUseCase: ISignOutUseCase
) : ViewModel(), IMyAccountViewModel {
    override val counter = MutableStateFlow(0)
    override val user = MutableStateFlow<User?>(null)

    override fun increment() {
        counter.value++
    }

    override fun getMe() {
        getMeUseCase.execute()
            .collectInScope(viewModelScope) { result ->
                when (result) {
                    is FlowResult.Success -> {
                        user.value = result.value
                        println("Got user details ${result.value}")
                    }
                    is FlowResult.Failure -> println("Error occurred: ${result.error}")
                }
            }
    }

    override fun signOut() = signOutUseCase.execute()

}
