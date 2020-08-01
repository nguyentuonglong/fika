package vn.com.corp.fika.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import vn.com.corp.fika.R
import vn.com.corp.fika.databinding.ActivityMainBinding
import vn.com.corp.fika.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        init()
        setUpViewModels()
    }

    private fun init() {

    }

    private fun setUpViewModels() {
        viewModel.userProfileLiveData.observe(this, Observer {
            binding.data = it
            binding.executePendingBindings()
        })
    }
}