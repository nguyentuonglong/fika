package vn.com.corp.fika.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import vn.com.corp.fika.R
import vn.com.corp.fika.databinding.ActivityMainBinding
import vn.com.corp.fika.util.AdapterImageSlider
import vn.com.corp.fika.util.Utils
import vn.com.corp.fika.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val imageAdapter: AdapterImageSlider by lazy { AdapterImageSlider() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        init()
        setUpViewModels()
    }

    private fun init() {
        viewModel.getUserProfile()
    }

    private fun setUpViewModels() {
        viewModel.userProfileLiveData.observe(this, Observer {
            mViewBinding.data = it
            mViewBinding.executePendingBindings()
            it.profile?.images?.let { imageList ->
                imageAdapter.setItems(imageList.filterNotNull())
                mViewBinding.pager.adapter = imageAdapter
                mViewBinding.indicator.setupViewPager(mViewBinding.pager)
                mViewBinding.pager.currentItem = 0
            }
            it.profile?.birthdate?.let { dobAsLong ->
                val dob = Utils.convertToDob(dobAsLong)
                val old = Utils.getYearOld(dobAsLong)
                mViewBinding.tvDob.text = String.format(getString(R.string.dob_holder), dob, old)
            }
        })
    }
}