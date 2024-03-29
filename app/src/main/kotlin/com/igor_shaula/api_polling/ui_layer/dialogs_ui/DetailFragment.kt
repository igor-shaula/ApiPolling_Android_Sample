package com.igor_shaula.api_polling.ui_layer.dialogs_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.igor_shaula.api_polling.R
import com.igor_shaula.api_polling.data_layer.VehicleRecord
import com.igor_shaula.api_polling.databinding.FragmentDetailBinding
import com.igor_shaula.api_polling.ui_layer.SharedViewModel
import timber.log.Timber

class DetailFragment : DialogFragment() {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var vehicleId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { theBundle ->
            vehicleId = theBundle.getString(VEHICLE_ID_KEY).toString()
            Timber.d("vehicleId = $vehicleId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.vehiclesMap.observe(viewLifecycleOwner) {
            updateUI(it[vehicleId])
        }
    }

    private fun updateUI(vehicleRecord: VehicleRecord?) {
        binding.apply {
            actvCounterOfNearVehicles.text = getString(
                R.string.close_distance_counter_text_base,
                viewModel.getNumberOfNearVehicles(),
                viewModel.getNumberOfAllVehicles()
            )
            actvVehicleId.text = getString(
                R.string.vehicleId, vehicleId
            )
            actvLiveStatus.text = getString(
                R.string.vehicleStatus,
                viewModel.vehiclesMap.value?.get(vehicleId)?.vehicleStatus?.uiStatus?.uppercase()
            )
            actvLiveCoordinates.text = getString(
                R.string.location_text_base,
                vehicleRecord?.currentLocation?.latitude,
                vehicleRecord?.currentLocation?.longitude
            )
        }
    }

    companion object {

        private const val DETAILS_FRAGMENT_TAG = "DetailFragment"
        private const val VEHICLE_ID_KEY = "vehicleId for this fragment"

        private fun newInstance(vehicleId: String) = DetailFragment().apply {
            arguments = Bundle(1).apply {
                putString(VEHICLE_ID_KEY, vehicleId)
            }
        }

        fun show(fragmentManager: FragmentManager, vehicleId: String) {
            if (fragmentManager.findFragmentByTag(DETAILS_FRAGMENT_TAG) == null) {
                newInstance(vehicleId).show(fragmentManager, DETAILS_FRAGMENT_TAG)
            }
        }
    }
}