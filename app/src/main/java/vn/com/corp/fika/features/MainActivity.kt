package vn.com.corp.fika.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import vn.com.corp.fika.R
import vn.com.corp.fika.adapter.GridRecyclerAdapter
import vn.com.corp.fika.databinding.ActivityMainBinding
import vn.com.corp.fika.util.AdapterImageSlider
import vn.com.corp.fika.util.SpaceItemDecoration
import vn.com.corp.fika.util.Utils
import vn.com.corp.fika.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val imageAdapter: AdapterImageSlider by lazy { AdapterImageSlider() }
    private val adapter by lazy { GridRecyclerAdapter() }

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
        mViewBinding.recyclerView.layoutManager = GridLayoutManager(this, 2).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 3 == 0)
                        2
                    else
                        1
                }
            }
        }
        mViewBinding.recyclerView.addItemDecoration(SpaceItemDecoration(5));
        mViewBinding.recyclerView.adapter = adapter
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
            //TODO: moment is a list so make a recyclerView hold list of moment, then item is a Grid recyclerView
            adapter.setData(it.profile?.moment?.get(0))
        })
    }
}