package com.example.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.movieapp.receiver.NetworkBroadcastReceiver
import com.example.movieapp.utils.Utils
import timber.log.Timber

abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean) -> T) :
    Fragment() {

    private var dialog: NetworkInfoDialogFragment? = null
    private var _binding: T? = null
    private var networkStateChanged = true
    val binding: T
        get() = _binding!!

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        initObserver()
        return _binding!!.root
    }

    private fun initObserver() {
        val networkReceiver =
            NetworkBroadcastReceiver(requireContext())
        networkReceiver.registerNetworkCallback()
        networkReceiver.networkAvailableLiveData.observe(viewLifecycleOwner) { networkAvailable ->
            if (networkAvailable) {
                if (networkStateChanged) {
                    viewCreated()
                    networkStateChanged = !networkStateChanged
                }
                dialog?.dismiss()
            } else {
                openDialog()
                networkStateChanged = true
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toast(text: String) {
        Timber.d("toast $text")
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkAndShowDialog(true)
    }

    private fun openDialog() {
        dialog = NetworkInfoDialogFragment(::checkNetworkAndShowDialog)
        dialog?.showNow(childFragmentManager, NetworkInfoDialogFragment.TAG)
    }

    private fun checkNetworkAndShowDialog(onlyCheck: Boolean = false) {
        if (!Utils.networkAvailable(requireContext())) {
            openDialog()
        } else {
            if (!onlyCheck)
                viewCreated()
        }
    }

    abstract fun viewCreated()
}