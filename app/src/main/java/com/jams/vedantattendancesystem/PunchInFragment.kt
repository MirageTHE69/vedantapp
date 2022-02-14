package com.jams.vedantattendancesystem

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jams.vedantattendancesystem.common.CommonUtil
import com.jams.vedantattendancesystem.common.CommonUtil.showLoadingDialog
import com.jams.vedantattendancesystem.model.punchInModel
import com.jams.vedantattendancesystem.viewmodel.CurrentEvent
import com.jams.vedantattendancesystem.viewmodel.punchInViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect


class PunchInFragment : Fragment() {


    lateinit var bitmap: Bitmap
    lateinit var locationEditext: EditText

    private var loading: Dialog? = null

    //lateinit var location: Location
   // var describeContents = 0.0
    //lateinit var addresses: List<Address>
 //   lateinit var geocoder: Geocoder
     var  Location  : String= ""
    lateinit var  viewmodel : punchInViewModel


    @RequiresApi(api = Build.VERSION_CODES.P)
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    private val MY_LOCATION_PERMISSION_CODE = 1

    lateinit var locationRequest: LocationRequest
    lateinit var punchinbtn : Button
    lateinit var punchOutBtn : Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


            checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,MY_LOCATION_PERMISSION_CODE)
            viewmodel = activity?.let {
                ViewModelProvider(it).get(punchInViewModel::class.java)
            } ?: throw Exception("Activity is null")


        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_punch_in, container, false)
        // Inflate the layout for this fragment


        locationEditext = view.findViewById<EditText>(R.id.LocationEditText)
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        punchinbtn = view.findViewById(R.id.PunchInNowBtn)

        locationEditext.setText(Location)


        punchOutBtn = view.findViewById(R.id.PunchOutnNowBtn)
        lifecycleScope.launchWhenStarted {
            viewmodel.punchEventFlow.collect {event->
                when(event){
                    is CurrentEvent.Success<*> -> {

                        view.findNavController().navigate(R.id.employeeDashBoard)

                    }
                    is CurrentEvent.Failure -> {
                        Snackbar.make(requireView(),event.errorText,Snackbar.LENGTH_LONG).show()

                    }
                    is CurrentEvent.Loading ->{
                        Log.d(TAG, "onCreateView: Loading")
                       Snackbar.make(requireView(),"loading...",LENGTH_LONG).show()
                    }
                    else -> Unit
                }

            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        punchinbtn.setOnClickListener {
            lifecycleScope.launchWhenStarted {

                val user_id = FirebaseAuth.getInstance().currentUser!!.uid
                getCurrentLocation(user_id,"IN")
                Log.d(TAG, "onStart: OnClick")
            }
        }

        punchOutBtn.setOnClickListener{
            val user_id = FirebaseAuth.getInstance().currentUser!!.uid
            getCurrentLocation(user_id,"OUT")

        }



    }
    private fun getCurrentLocation(user_id:String,punchType : String) :String{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity()?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED
            ) {
                if (isGPSEnabled()) {
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                        .requestLocationUpdates(locationRequest, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                LocationServices.getFusedLocationProviderClient(requireActivity())
                                    .removeLocationUpdates(this)
                                if (locationResult.getLocations().size > 0) {
                                    val index: Int = locationResult.getLocations().size - 1
                                    val latitude: Double =
                                        locationResult.getLocations().get(index).getLatitude()
                                    val longitude: Double =
                                        locationResult.getLocations().get(index).getLongitude()
                                    android.util.Log.d(
                                        "map",
                                        "onLocationResult: " + locationResult.getLocations()
                                    )
                                    Location = "https://maps.google.com/?q=$latitude,$longitude"

                                        viewmodel.createPunch(punchInModel(null, punchType, user_id, location = Location))
                                    Log.d("location",""+Location)

                                }
                            }
                        }, Looper.getMainLooper())
                    return Location
                } else {
                    turnOnGPS()
                }
            } else {
                requestPermissions(
                    arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
        return Location

    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {

                // Requesting the permission
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
            } else {
                Toast.makeText(activity, "Permission already granted", Toast.LENGTH_SHORT).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                activity.startActivityFromFragment(this@PuchInFragment, cameraIntent, CAMERA_REQUEST)
            }
        }
        if (requestCode == MY_LOCATION_PERMISSION_CODE) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {

                // Requesting the permission
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
            } else {
                Log.d("Allowed", "checkPermission: location")
            }
        }
    }



    private fun isGPSEnabled(): Boolean {
        var locationManager: LocationManager? = null
        var isEnabled = false
        if (locationManager == null) {
            locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        }
        isEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

        return isEnabled
    }


    private fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(Activity())
                .checkLocationSettings(builder.build())
        result.addOnCompleteListener(OnCompleteListener<LocationSettingsResponse?> { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                Toast.makeText(activity, "GPS is already tured on", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(requireActivity(), 2)
                    } catch (ex: SendIntentException) {
                        ex.printStackTrace()
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        })
    }

}