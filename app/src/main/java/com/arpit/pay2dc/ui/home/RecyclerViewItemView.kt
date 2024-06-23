package com.arpit.pay2dc.ui.home

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.AbstractComposeView
import androidx.lifecycle.ViewModelProvider
import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.ui.MainActivity
import com.arpit.pay2dc.ui.RecyclerView
import com.arpit.pay2dc.ui.theme.RecyclerViewTransactionTheme

class RecyclerViewItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private val currencies = mutableStateListOf<Currency?>()
    private lateinit var viewModel: MainViewModel

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel =
            ViewModelProvider((context as ContextWrapper).baseContext as MainActivity)[MainViewModel::class.java]
    }

    @Composable
    override fun Content() {
        RecyclerViewTransactionTheme {
            RecyclerView(currencies)
        }
    }

    fun updateUi(model: List<Currency?>) {
        currencies.clear()
        currencies.addAll(model)
    }
}