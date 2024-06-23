package com.arpit.pay2dc.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.ResultData
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.domain.FetchCurrencyConversionUseCase
import com.arpit.pay2dc.domain.FetchCurrencyDataUseCase
import com.arpit.pay2dc.extension.objectToJson
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchCurrencyDataUseCase: FetchCurrencyDataUseCase,
    private val fetchCurrencyConversionUseCase: FetchCurrencyConversionUseCase,
    private val moshi: Moshi
) : ViewModel() {

    private val _state =
        MutableSharedFlow<Result<List<Currency?>>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val state = _state.asSharedFlow()

    private val _stateCurrenciesConversion =
        MutableSharedFlow<Result<CurrencyConversionData>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val stateCurrenciesConversion = _stateCurrenciesConversion.asSharedFlow()
    private val map: HashMap<String, Float> = HashMap()
    private var selectedCurrency: String? = null
    private var amount: Float = 0F
    private var arrayListCurrencies: ArrayList<Currency?> = ArrayList()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        println("Exception caught ${e.message}")
    }

    init {
        fetchCurrencies()
        fetchCurrentConversion()
    }

    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.tryEmit(fetchCurrencyDataUseCase.fetchData())
        }
/*
        *//**
         * Exception handling using coroutineExceptionHandler in parent launch
         *//*
        viewModelScope.launch(coroutineExceptionHandler) {
            launch {
                throw IllegalStateException("xhxhxhx Exception handling inside parent coroutineExceptionHandler")
            }
        }

        *//**
         * Exception handling inside supervisor scope
         *//*
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            supervisorScope {
                launch {
                    println("xhxhxhx First coroutine launched")
                    launch {
                        throw IllegalStateException("xhxhxhx Exception handling inside supervisor scope")
                    }
                }
                launch {
                    println("xhxhxhx Second coroutine launched")
                }
            }
        }


        *//**
         * Exception handling using coroutine scope using coroutineExceptionHandler
         *//*

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            coroutineScope {
                launch {
                    println("xhxhxhx First coroutine launched inside coroutine scope coroutineExceptionHandler")
                    launch {
                        throw IllegalStateException("xhxhxhx Exception handling inside coroutine scope coroutineExceptionHandler")
                    }
                }
                launch {
                    println("xhxhxhx Second coroutine launched inside coroutine scope coroutineExceptionHandler")
                }
            }
        }

        *//**
         * Exception handling using coroutine scope using try/catch
         *//*

        viewModelScope.launch(Dispatchers.IO) {
            try {
                coroutineScope {
                    launch {
                        println("xhxhxhx First coroutine launched inside coroutine scope")
                        launch {
                            throw IllegalStateException("xhxhxhx Exception handling inside coroutine scope using try catch")
                        }
                    }
                    launch {
                        println("xhxhxhx Second coroutine launched inside coroutine scope")
                    }
                }
            } catch (e: IllegalStateException) {
                println("Exception caught inside try catch ${e.message}")
            }
        }*/
    }

    fun fetchCurrentConversion() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = fetchCurrencyConversionUseCase.fetchData()) {
                is Result.Success -> {
                    _stateCurrenciesConversion.tryEmit(result)
                    val res = iterateOnJson(result.data.rates, moshi)
                    map.putAll(res)
                }

                is Result.Error -> {
                    _stateCurrenciesConversion.tryEmit(result)
                    println(result.exception.stackTrace)
                }
            }
        }
    }

    fun setCurrenciesList(list: List<Currency?>) {
        arrayListCurrencies.clear()
        arrayListCurrencies.addAll(list)
    }

    fun setSelectedCurrency(cur: String) {
        selectedCurrency = cur
        if (amount > 0) {
            doConversion(getAmount(amount, cur), cur)
        }
    }

    fun getSelectedCurrency(): String? {
        return selectedCurrency
    }

    fun setEnteredAmount(amount: String, cur: String?) {
        this.amount = 0F
        if (amount.isNotEmpty())
            this.amount = amount.toFloat()
        doConversion(getAmount(this.amount, cur), cur)
    }

    private fun doConversion(amount: Float, cur: String?) {
        cur?.let {
            val res: List<Currency?> = arrayListCurrencies.map {
                getMap()[it?.name]?.let { conversionRate ->
                    it?.copy(amount = amount * conversionRate)
                }
            }
            _state.tryEmit(Result.Success((res)))
        }
    }

    fun getAmount(amount: Float, cur: String?) = getAmountInUsd(amount, cur)

    /**
     * base currency is USD, so we are converting the entered amount to USD so that
     * the other conversions can be performed directly
     */
    private fun getAmountInUsd(amount: Float, cur: String?): Float {
        getMap()[cur]?.let {
            if (cur != "USD") {
                return amount * 1F / it
            }
        }
        return amount
    }

    fun getMap(): HashMap<String, Float> {
        return map
    }

    fun iterateOnJson(data: ResultData, moshi: Moshi) = iterateOnJsonObject(data, moshi)

    private fun iterateOnJsonObject(data: ResultData, moshi: Moshi): HashMap<String, Float> {
        val map: HashMap<String, Float> = HashMap()
        moshi.objectToJson(data).let { json ->
            val jsonObject = JSONObject(json)
            val keys = jsonObject.keys()

            while (keys.hasNext()) {
                val key = keys.next()
                jsonObject.opt(key)?.let {
                    map[key] = it.toString().trim().toFloat()
                }
            }
        }

        map["VEF"] = 3615460F
        return map
    }
}