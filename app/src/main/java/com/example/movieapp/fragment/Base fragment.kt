package com.example.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.movieapp.utils.Utils
import timber.log.Timber

open class BaseFragment<T : ViewBinding>(
    private val bindingInflater: (
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean,
    ) -> T,
) :
    Fragment() {

    private var _binding: T? = null
    val binding: T
        get() = _binding!!

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkAndShowDialog()
    }

    private fun checkNetworkAndShowDialog() {
        if (!Utils.networkAvailable(requireContext())) {
            val dialog = NetworkInfoFragment(::checkNetworkAndShowDialog)
            dialog.showNow(childFragmentManager, NetworkInfoFragment.TAG)
        } else {
            viewCreated()
        }
    }

    open fun viewCreated() {
        Timber.d("viewCreated BaseFragment")
    }
}