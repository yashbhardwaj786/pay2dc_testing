package com.arpit.pay2dc.di

import com.arpit.pay2dc.data.repository.FetchCurrencyConversionRepository
import com.arpit.pay2dc.data.repository.MainRepository
import com.arpit.pay2dc.domain.IFetchCurrencyConversionRepository
import com.arpit.pay2dc.domain.IMainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DomainModule {
    @Binds
    fun provideCurrencyConversionRepo(fetchCurrencyConversionRepository: FetchCurrencyConversionRepository): IFetchCurrencyConversionRepository

    @Binds
    fun provideMainRepo(fetchMainRepository: MainRepository): IMainRepository

}