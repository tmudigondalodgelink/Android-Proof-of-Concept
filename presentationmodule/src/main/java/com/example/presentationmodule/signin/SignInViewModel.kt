package com.example.presentationmodule.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domainmodule.usecases.FakeSignInUseCase
import com.example.domainmodule.usecases.ISignInUseCase
import com.example.domainmodule.utilities.FlowResult
import com.example.presentationmodule.collectInScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface ISignInViewModel {
    val email: StateFlow<String>
    val password: StateFlow<String>
    val onSignInSuccess: StateFlow<Boolean>

    fun setEmail(email: String)
    fun setPassword(password: String)
    fun singIn()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: ISignInUseCase
): ViewModel(), ISignInViewModel {
    override val email: MutableStateFlow<String> = MutableStateFlow("")
    override val password: MutableStateFlow<String> = MutableStateFlow("")
    override val onSignInSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun setEmail(email: String) {
        this.email.value = email
    }

    override fun setPassword(password: String) {
        this.password.value = password
    }

    override fun singIn() {
        signInUseCase.execute(email.value, password.value)
            .collectInScope(viewModelScope) { result ->
                when (result) {
                    is FlowResult.Success -> {
                        onSignInSuccess.value = true
                        println("Signed in with ${result.value}")
                    }
                    is FlowResult.Failure -> println("Error occurred: ${result.error}")
                }
            }
    }

    companion object {
        fun fake(): SignInViewModel {
            return  SignInViewModel(signInUseCase = FakeSignInUseCase())
        }
    }
}