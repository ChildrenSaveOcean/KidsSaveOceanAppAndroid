package com.kidssavetheocean.fatechanger.bottomNavigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.dashboard.MainDashboardFragment
import com.kidssavetheocean.fatechanger.databinding.ActivityBottomNavigationBinding
import com.kidssavetheocean.fatechanger.home.HomeFragment
import com.kidssavetheocean.fatechanger.map.MapFragment
import com.kidssavetheocean.fatechanger.news.NewsFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssavetheocean.fatechanger.resources.ResourcesFragment
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
                else -> error("Menu item id not implemented!")
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
