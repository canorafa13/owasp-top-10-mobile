package com.owasp.top.mobile.demo.domain

import androidx.lifecycle.ViewModel
import com.owasp.top.mobile.demo.domain.repository.OwaspRepository
import com.owasp.top.mobile.demo.environment.FlavorApp
import com.owasp.top.mobile.demo.ui.data.InjectData
import com.owasp.top.mobile.demo.ui.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val owaspRepository: OwaspRepository,
    private val userData: InjectData<UserData>
) : ViewModel(){

    fun provideUserData(): UserData? {
        if (FlavorApp.isSecure()) {
            return userData.value
        }
        return owaspRepository.getUserData()
    }
}