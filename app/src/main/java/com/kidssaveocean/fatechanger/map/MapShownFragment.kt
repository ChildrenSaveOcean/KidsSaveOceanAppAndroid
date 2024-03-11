package com.kidssaveocean.fatechanger.map

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentMapShownBinding
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.views.CustomMapMarkerView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Observable
import java.util.Observer

//todo fix
@AndroidEntryPoint
class MapShownFragment : AbstractFragment<FragmentMapShownBinding, EmptyViewModel>(), OnMapReadyCallback, Observer {

    private var countries: MutableCollection<CountryModel> = mutableListOf()
    private lateinit var mGoogleMap: GoogleMap

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_map_shown

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //todo what ????
        val fragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        fragment!!.getMapAsync(this)

        FirebaseService.getInstance().addObserver(this)

    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is FirebaseService -> {
                if (o.countries.isNotEmpty()) {
                    drawMarks()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isMapToolbarEnabled = false
        mGoogleMap = googleMap
        drawMarks()
    }

    private fun drawMarks() {
        countries.clear()
        countries.addAll(FirebaseService.getInstance().countries)

        for (item in countries) {
            if (item.country_number > 0) {
                val country = LatLng(item.latitude, item.longitude)

                CustomMapMarkerView.numberLetter = item.country_number.toString()
                val drawable = activity?.let { createDrawableFromView(it, CustomMapMarkerView(it)) }!!
                val icon = BitmapDescriptorFactory.fromBitmap(drawable)

                if (icon is BitmapDescriptor){
                    mGoogleMap.addMarker(MarkerOptions()
                            .position(country)
                            .icon(icon)
                            .title(item.country_name))
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(country))
                }
            }
        }
    }


    private fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()

        val bitmap: Bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}
