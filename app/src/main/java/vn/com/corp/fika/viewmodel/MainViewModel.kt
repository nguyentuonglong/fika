package vn.com.corp.fika.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import vn.com.corp.fika.model.UserProfile
import vn.com.corp.fika.network.ApiService


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _userProfileLiveData = MutableLiveData<UserProfile>()
    val userProfileLiveData: LiveData<UserProfile>
        get() = _userProfileLiveData
    private val compositeDisposable = CompositeDisposable()

    fun getUserProfile() {
        compositeDisposable.add(
            ApiService.getInstance().getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _userProfileLiveData.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    companion object {

    }
}