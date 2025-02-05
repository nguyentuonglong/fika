package vn.com.corp.fika.features

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import vn.com.corp.fika.R
import vn.com.corp.fika.adapter.GridRecyclerAdapter
import vn.com.corp.fika.databinding.ActivityMainBinding
import vn.com.corp.fika.util.AdapterImageSlider
import vn.com.corp.fika.util.ProgressDialog
import vn.com.corp.fika.util.SpaceItemDecoration
import vn.com.corp.fika.util.Utils
import vn.com.corp.fika.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val imageAdapter by lazy { AdapterImageSlider() }
    private val momentAdapter by lazy { GridRecyclerAdapter() }
    private val loading by lazy { ProgressDialog.progressDialog(this) }

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
        loading.show()
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
        mViewBinding.recyclerView.adapter = momentAdapter
        mViewBinding.imgHeart.setOnClickListener {
            (it as ImageView).setImageResource(R.drawable.ic_filled_favorite_24)
            val scaleDown: ValueAnimator? = ObjectAnimator.ofPropertyValuesHolder(
                it,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
            )
            scaleDown?.duration = 300
            scaleDown?.repeatCount = 3
            scaleDown?.repeatMode = ObjectAnimator.RESTART
            scaleDown?.start()
            scaleDown?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    showLikeDialog();
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })
        }
    }

    private fun setUpViewModels() {
        viewModel.userProfileLiveData.observe(this, Observer {
            loading.hide()
            mViewBinding.data = it
            mViewBinding.executePendingBindings()
            mViewBinding.mainContainer.isVisible = true
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
            /**
             * TODO: moment is a list so make a bigger recyclerView hold list of moment, then item is a small recyclerView in there
             */
            momentAdapter.setData(it.profile?.moment?.get(0))
        })
    }

    private fun showLikeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom_layout, null)
        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
        val btnDismiss = dialogView.findViewById<Button>(R.id.btDismissCustomDialog)
        btnDismiss.setOnClickListener {
            customDialog.dismiss()
        }
    }
}