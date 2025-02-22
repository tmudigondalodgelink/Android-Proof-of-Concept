package com.example.architecturepoc2

import android.content.Context
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import android.view.View.inflate
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.architecturepoc2.databinding.FragmentContainerBinding
import com.example.architecturepoc2.ui.theme.ArchitecturePOC2Theme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import kotlin.reflect.KClass

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitecturePOC2Theme {
                val items = listOf(
                    BottomNavigationItem(
                    title = "Search",
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search),
                    BottomNavigationItem(
                        title = "Bookings",
                        selectedIcon = Icons.Filled.Email,
                        unselectedIcon = Icons.Outlined.Email),
                    BottomNavigationItem(
                        title = "MyAccount",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings)

                )

                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                val navController = rememberNavController()

                val context = LocalContext.current

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed {index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        when (index) {
                                            0 -> navController.navigate("search")
                                            1 -> navController.navigate("bookings")
                                            2 -> navController.navigate("myAccount")
                                            else -> navController.navigate("search")
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    },
                                    label = { Text(item.title) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "search",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("search") { FragmentHostComposable(SearchFragment::class, "searchFragment") }
                        composable("bookings") { FragmentHostComposable(BookingsFragment::class, "bookingsFragment") }
                        composable("myAccount") { FragmentHostComposable(MyAccountFragment::class, "myAccountFragment") }
                    }
                }
            }
        }
    }
}

@Composable
fun FragmentHostComposable(fragment: Fragment, context: Context) {
    val fragmentActivity = context as? FragmentActivity ?: return

    AndroidViewBinding(FragmentContainerBinding::inflate) {
        val transaction = fragmentActivity.supportFragmentManager.beginTransaction()
        transaction.replace(fragmentContainer.id, fragment)
        transaction.commit()
    }
}

@Composable
fun FragmentHostComposable(fragmentClass: KClass<out Fragment>, tag: String) {
    val fragmentActivity = LocalActivity.current as FragmentActivity
    val fragmentManager = fragmentActivity.supportFragmentManager
    val containerId = remember { View.generateViewId() }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            FrameLayout(context).apply { id = containerId }
        },
        update = { view ->
            val existingFragment = fragmentManager.findFragmentByTag(tag)

            fragmentManager.beginTransaction().apply {
                if (existingFragment != null) {
                    remove(existingFragment) // Remove the old fragment before adding a new one
                }
                val newFragment = fragmentClass.java.newInstance()
                replace(view.id, newFragment, tag)
                commitNow()
            }
        }
    )
}


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)