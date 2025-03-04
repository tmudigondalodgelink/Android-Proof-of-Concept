package com.example.architecturepoc2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

//class BookingsFragmentFactory @Inject constructor(
//    private val bookingsViewModel: BookingsViewModel
//) : FragmentFactory() {
//
//    var navigateToPropertyDetails: () -> Unit = {}
//
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//        return when (className) {
//            BookingsFragment::class.java.name -> BookingsFragment(bookingsViewModel)
//            else -> super.instantiate(classLoader, className)
//        }
//    }
//}
