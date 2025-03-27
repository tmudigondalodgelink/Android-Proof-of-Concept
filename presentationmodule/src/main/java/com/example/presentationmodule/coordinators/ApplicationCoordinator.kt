import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domainmodule.models.Authentication
import com.example.presentationmodule.ApplicationCoordinatorViewModel
import com.example.presentationmodule.BookingsFragment
import com.example.presentationmodule.ComposableLifecycle
import com.example.presentationmodule.IApplicationViewModel
import com.example.presentationmodule.MainScreen
import com.example.presentationmodule.MyAccountView
import com.example.presentationmodule.NestedFragment1
import com.example.presentationmodule.NestedFragment2
import com.example.presentationmodule.NestedFragment4
import com.example.presentationmodule.NestedView3
import com.example.presentationmodule.PropertyDetailsView
import com.example.presentationmodule.SearchFragment
import com.example.presentationmodule.coordinators.AuthenticationCoordinator
import com.example.presentationmodule.coordinators.MainCoordinator
import com.example.presentationmodule.signin.SignInView

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