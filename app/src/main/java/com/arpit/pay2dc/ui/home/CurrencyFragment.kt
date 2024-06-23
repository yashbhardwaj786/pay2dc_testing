package com.arpit.pay2dc.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.arpit.pay2dc.databinding.FragmentCurrencyBinding
import com.arpit.pay2dc.ui.ShowOutlinedTextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.extension.showSnackbar
import com.arpit.pay2dc.ui.SpinnerSample
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding?.progressCircular?.visibility = View.VISIBLE
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is Result.Success -> {
                        binding?.progressCircular?.visibility = View.GONE
                        binding?.rowView?.updateUi(it.data)
                        mainViewModel.setCurrenciesList(it.data)
                        if(mainViewModel.getSelectedCurrency() == null) {
                            binding?.composeView?.apply {
                                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                                setContent {
                                    MaterialTheme {
                                        UiLayout(it.data.map { cur ->
                                            cur?.name
                                        })
                                    }
                                }
                            }
                        }
                    }

                    is Result.Error -> {
                        binding?.progressCircular?.visibility = View.GONE
                        showSnackbar(it.exception.message ?: it.exception.localizedMessage ?: "No data available")
                    }
                }
            }
        }
    }

    private fun showSnackbar(error: String) {
        binding?.root?.showSnackbar(error) {
            mainViewModel.fetchCurrencies()
        }
    }

    @Composable
    fun UiLayout(list: List<String?>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
            horizontalAlignment = Alignment.End
        ) {
            ShowOutlinedTextField(modifier = Modifier.fillMaxWidth()) {
                mainViewModel.setEnteredAmount(it, mainViewModel.getSelectedCurrency())
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            if(list.isNotEmpty())
            SpinnerSample(list = list, preselected = list[0]) {
                mainViewModel.setSelectedCurrency(it)
            }
        }
    }

}