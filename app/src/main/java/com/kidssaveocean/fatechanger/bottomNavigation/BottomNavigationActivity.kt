package com.kidssaveocean.fatechanger.bottomNavigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.dashboard.MainDashboardFragment
import com.kidssaveocean.fatechanger.databinding.ActivityBottomNavigationBinding
import com.kidssaveocean.fatechanger.home.HomeFragment
import com.kidssaveocean.fatechanger.map.MapFragment
import com.kidssaveocean.fatechanger.news.NewsFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class BottomNavigationActivity : AbstractActivity<ActivityBottomNavigationBinding, EmptyViewModel>() {

    private var isInnerNavigation: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BottomNavigationViewHelper.removeShiftMode(binding.bottomNavigationBar)
        binding.bottomNavigationBar.setOnNavigationItemSelectedListener(setUpOnNavigationItemSelectedListener())

        openView(HomeFragment::class)
    }

    override fun getLayoutId(): Int = R.layout.activity_bottom_navigation

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getContainerViewId(): Int = R.id.fragment_container

    fun setMenuItem(itemId: Int) {
        binding.bottomNavigationBar.selectedItemId = itemId
    }

    private fun setUpOnNavigationItemSelectedListener(): BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (isInnerNavigation) {
                isInnerNavigation = false
                return@OnNavigationItemSelectedListener true
            }
            val classz = when (menuItem.itemId) {
                R.id.action_home -> HomeFragment::class
                R.id.action_news -> NewsFragment::class
                R.id.action_dashboard -> MainDashboardFragment::class
                R.id.action_resources -> ResourcesFragment::class
                R.id.action_map -> MapFragment::class
                else -> throw IllegalStateException("Menu item id not implemented!")
            }
            isInnerNavigation = true
            openView(classz)
            true
        }

    override fun <T : Any> onViewChanged(classz: KClass<T>) {
        super.onViewChanged(classz)
        if(isInnerNavigation){
            isInnerNavigation = false
            return
        }
        val itemId = when (classz) {
            HomeFragment::class -> R.id.action_home
            NewsFragment::class -> R.id.action_news
            MainDashboardFragment::class -> R.id.action_dashboard
            ResourcesFragment::class -> R.id.action_resources
            MapFragment::class -> R.id.action_map
            else -> 0
        }
        if (itemId != 0) {
            isInnerNavigation = true
            setMenuItem(itemId)
        }
    }
}
