package com.htf.myweatherlibrary.base

import android.app.Dialog

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*

@Suppress("DEPRECATION")
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel>(private val viewModelClass: Class<V>) :
    AppCompatActivity(), LifecycleObserver {
    lateinit var viewModel: V
    var binding: T?=null
    open val layout: Int = -1
    open val initializeViewModel: V.() -> Unit = {}
    var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel<V>()
        bind<T>(layout)
        initializeViewModel(viewModel)

    }

    private fun <V : BaseViewModel> obtainViewModel() =
        ViewModelProvider(this)[viewModelClass].apply {
            lifecycle.addObserver(this@BaseActivity)
        }


    private fun <C : ViewDataBinding> bind(layout: Int) {
        binding = DataBindingUtil.setContentView<T>(this, layout).apply {
            lifecycleOwner = this@BaseActivity
            setVariable(layout, viewModel)
            executePendingBindings()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
        progressDialog?.dismiss()
    }

}