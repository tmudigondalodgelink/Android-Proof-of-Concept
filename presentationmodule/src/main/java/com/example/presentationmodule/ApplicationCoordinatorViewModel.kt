package com.example.presentationmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domainmodule.models.Authentication
import com.example.domainmodule.usecases.IUserAuthenticatedUseCase
import com.example.domainmodule.utilities.FlowResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface IApplicationCoordinatorViewModel {
    val authentication: StateFlow<Authentication>

    fun initialize()
}

@HiltViewModel
class ApplicationCoordinatorViewModel @Inject constructor(
    private val authenticatedUseCase: IUserAuthenticatedUseCase
): ViewModel(), IApplicationCoordinatorViewModel {
    override val authentication: MutableStateFlow<Authentication> = MutableStateFlow(Authentication.Unauthenticated)

    override fun initialize() {
        authenticatedUseCase.execute()
            .collectInScope(viewModelScope) { authentication ->
                this.authentication.value = authentication
            }
    }
}