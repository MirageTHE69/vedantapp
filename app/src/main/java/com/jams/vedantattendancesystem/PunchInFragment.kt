package com.jams.vedantattendancesystem

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment


class PunchInFragment : Fragment() {

    val pic_id = 123
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var bitmap: Bitmap
    lateinit var locationEditext: EditText
    lateinit var Camera: Button
    lateinit var PunchInNowBtn:android.widget.Button
    lateinit var imageView: ImageView
    lateinit var location: Location
    var describeContents = 0.0
    lateinit var addresses: List<Address>
    lateinit var geocoder: Geocoder

    @RequiresApi(api = Build.VERSION_CODES.P)
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_punch_in, container, false)
        // Inflate the layout for this fragment

        Camera = view.findViewById<Button>(R.id.CameraBtn)
        imageView = view.findViewById<ImageView>(R.id.imageView)

        locationEditext = view.findViewById<EditText>(R.id.LocationEditText)
        val locationManager = getActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION, Man,
                    Camera.setOnClickListener {
                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(
                                arrayOf(Manifest.permission.CAMERA),
                                .MY_CAMERA_PERMISSION_CODE
                            )
                        } else {
                            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(
                                cameraIntent,
                                com.jams.vedantattendancesystem.PunchInFragment.pic_id
                            )
                        }
                    })
            )


        }

        return view
    }


}