import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.presentationmodule.BookingsFragment
import com.example.presentationmodule.MyAccountView
import com.example.presentationmodule.NestedFragment1
import com.example.presentationmodule.NestedFragment2
import com.example.presentationmodule.NestedFragment4
import com.example.presentationmodule.NestedView3
import com.example.presentationmodule.PropertyDetailsView
import com.example.presentationmodule.SearchFragment
import com.example.presentationmodule.signin.SignInView

@Composable
fun MainCoordinator(navController: NavHostController, innerPadding: PaddingValues) {
    val searchFragmentState = rememberFragmentState()

    NavHost(
        navController = navController,
        startDestination = "search",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("signIn") { SignInView() }

        composable("search") {
            AndroidFragment<SearchFragment>(
                modifier = Modifier.fillMaxSize(),
                fragmentState = searchFragmentState
            ) {
                it.navigateToNestedFragment1 = {
                    navController.navigate("nestedFragment1") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }

        composable("bookings") {
            val bookingFragmentState = rememberFragmentState()
            AndroidFragment<BookingsFragment>(
                modifier = Modifier.fillMaxSize(),
                fragmentState = bookingFragmentState,
            ) {
                it.navigateToPropertyDetails = {
                    navController.navigate("propertyDetails") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
        composable("myAccount") { MyAccountView() }

        composable("propertyDetails") { PropertyDetailsView(navigateToNestedView3 = {
            navController.navigate("nestedView3") {
                launchSingleTop = true
                restoreState = true
            }
        }) }

        composable("nestedFragment1") {
            AndroidFragment<NestedFragment1>(
                modifier = Modifier.fillMaxSize(),
            ) {
                it.navigateToNestedFragment2 = {
                    navController.navigate("nestedFragment2") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            } }

        composable("nestedFragment2") {
            AndroidFragment<NestedFragment2>(
                modifier = Modifier.fillMaxSize(),
            ) }

        composable("nestedView3") { NestedView3(navigateToNestedFragment4 = {
            navController.navigate("nestedFragment4") {
                launchSingleTop = true
                restoreState = true
            }
        }) }

        composable("nestedFragment4") {
            AndroidFragment<NestedFragment4>(
                modifier = Modifier.fillMaxSize(),
            ) }
    }
}