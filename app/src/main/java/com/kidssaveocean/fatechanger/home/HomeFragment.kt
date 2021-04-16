package com.kidssaveocean.fatechanger.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.WebViewActivity
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.countryContacts.CountryIntroFragment
import com.kidssaveocean.fatechanger.dashboard.MainDashboardFragment
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.map.MapFragment
import com.kidssaveocean.fatechanger.news.NewsFragment
import com.kidssaveocean.fatechanger.policy.PolicyHomeActivity
import com.kidssaveocean.fatechanger.policy.PolicyVideoActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class HomeFragment : Fragment(), HomeRecyclerAdapter.ItemClick {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val recyclerview: RecyclerView = view.findViewById(R.id.home_recyclerview)

        var adapter = HomeRecyclerAdapter(activity as Context)
        adapter.setItemClickListener(this)

        recyclerview?.adapter = adapter
        recyclerview?.layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onItemClick(v: View, position: Int) {
        val bottomActivity = activity as BottomNavigationActivity

        when (position) {
            Operator.NEWS_MEDIA.ordinal -> {
                bottomActivity.setMenuItem(R.id.action_news)
            }

            Operator.LETTER_IMPACT.ordinal -> {
                CountryIntroFragment().addToNavigationStack(
                        bottomActivity.supportFragmentManager,
                        R.id.fragment_container,
                        "letter_impact_fragment")
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
