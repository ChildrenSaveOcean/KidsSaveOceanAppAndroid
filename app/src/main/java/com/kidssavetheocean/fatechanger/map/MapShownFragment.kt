package com.kidssavetheocean.fatechanger.map

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentMapShownBinding
import com.kidssavetheocean.fatechanger.firebase.FirebaseService
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssavetheocean.fatechanger.views.CustomMapMarkerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

//todo fix
@AndroidEntryPoint
class MapShownFragment : AbstractFragment<FragmentMapShownBinding, EmptyViewModel>(),
    OnMapReadyCallback {

    private lateinit var mGoogleMap: GoogleMap

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_map_shown

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    //todo fix this temp solution
    @Inject
    lateinit var firebaseService: FirebaseService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //todo what ????
        val fragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        fragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isMapToolbarEnabled = false
        mGoogleMap = googleMap
        drawMarks(emptyList())
        firebaseService.countries.observe(this.viewLifecycleOwner) {
            drawMarks(it)
        }
        runBlocking {
            firebaseService.getCountriesData()
        }
    }

    private fun drawMarks(countries: List<CountryModel>) {

        for (item in countries) {
            if (item.letters_written_to_country > 0) {
                val country = LatLng(item.latitude, item.longitude)

                CustomMapMarkerView.numberLetter = item.letters_written_to_country.toString()
                val drawable =
                    activity?.let { createDrawableFromView(it, CustomMapMarkerView(it)) }!!
                val icon = BitmapDescriptorFactory.fromBitmap(drawable)
                mGoogleMap.addMarker(
                    MarkerOptions()
                        .position(country)
                        .icon(icon)
                        .title(item.country_name)
                )
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(country))
            }
        }
    }

    private fun createDrawableFromView(context: Context, view: View): Bitmap {
        //todo fix this
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()

        val bitmap: Bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}
