import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.domainmodule.models.Authentication
import com.example.presentationmodule.ApplicationCoordinatorViewModel
import com.example.presentationmodule.myaccount.ComposableLifecycle
import com.example.presentationmodule.IApplicationViewModel
import com.example.presentationmodule.coordinators.AuthenticationCoordinator
import com.example.presentationmodule.coordinators.MainCoordinator

@Composable
fun ApplicationCoordinator(viewModel: IApplicationViewModel = hiltViewModel<ApplicationCoordinatorViewModel>()) {
    val authenticationState by viewModel.authentication.collectAsState()

    ComposableLifecycle { source, event ->
        when (event) {
            Lifecycle.Event.ON_START -> { viewModel.initialize() }
            else -> {}
        }
    }

    when (authenticationState) {
        is Authentication.Authenticated -> {
            MainCoordinator()
        }
        is Authentication.Unauthenticated -> {
            AuthenticationCoordinator()
        }
    }
}