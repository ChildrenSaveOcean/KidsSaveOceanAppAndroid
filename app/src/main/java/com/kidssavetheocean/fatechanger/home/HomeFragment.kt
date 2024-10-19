package com.kidssavetheocean.fatechanger.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.WebViewActivity
import com.kidssavetheocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssavetheocean.fatechanger.countryContacts.CountryIntroFragment
import com.kidssavetheocean.fatechanger.databinding.HomeFragmentBinding
import com.kidssavetheocean.fatechanger.policy.PolicyHomeActivity
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : AbstractFragment<HomeFragmentBinding, EmptyViewModel>(), HomeRecyclerAdapter.ItemClick {

    private enum class Operator {
        NEWS_MEDIA,
        LETTER_IMPACT,
        POLICY,
        DASHBOARD,
        MAP,
        SHARE_LIKE_A_BOSS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeFragment", "onCreate")
    }

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        val recyclerview: RecyclerView = binding.homeRecyclerview

        val adapter = HomeRecyclerAdapter(requireContext())
        adapter.setItemClickListener(this)

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.home_fragment

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun onItemClick(v: View, position: Int) {
        //todo fix
        val bottomActivity = activity as BottomNavigationActivity

        when (position) {
            Operator.NEWS_MEDIA.ordinal -> {
                bottomActivity.setMenuItem(R.id.action_news)
            }

            Operator.LETTER_IMPACT.ordinal -> {
                navigateToView(CountryIntroFragment::class)
            }

            Operator.DASHBOARD.ordinal -> {
                bottomActivity.setMenuItem(R.id.action_dashboard)
            }

            Operator.POLICY.ordinal ->
                startActivity(Intent(activity, PolicyHomeActivity::class.java))

            Operator.MAP.ordinal -> bottomActivity.setMenuItem(R.id.action_map)

            Operator.SHARE_LIKE_A_BOSS.ordinal -> {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra(Constants.INTENT_URL, Constants.URL_SHARE_LIKE_A_BOSS)
                startActivity(intent)
            }
        }
    }


}
