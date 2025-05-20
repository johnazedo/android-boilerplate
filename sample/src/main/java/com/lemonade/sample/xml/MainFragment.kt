package com.lemonade.sample.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lemonade.android_boilerplate.fragment.onReceiveEvent
import com.lemonade.sample.databinding.XmlMainFragmentBinding

class MainFragment: Fragment() {

    private var _binding: XmlMainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel = MainFragmentViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = XmlMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onReceiveEvent(viewModel)
        binding.loginFragmentButton.setOnClickListener {
            viewModel.navigateToLoginFragment()
        }
        binding.homeFragmentButton.setOnClickListener {
            viewModel.navigateToHomeFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}