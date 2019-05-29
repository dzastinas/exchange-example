package net.justinas.exercise.lib.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.justinas.exercise.lib.databinding.FragmentExchangeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeFragment : Fragment() {

    val viewModel: ExchangeViewModel by viewModel()

    private lateinit var binding: FragmentExchangeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExchangeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.result.value = null
    }
}