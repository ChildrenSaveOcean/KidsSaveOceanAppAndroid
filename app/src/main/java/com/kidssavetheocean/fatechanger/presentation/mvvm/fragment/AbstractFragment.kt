package com.kidssavetheocean.fatechanger.presentation.mvvm.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kidssavetheocean.fatechanger.presentation.AbstractViewModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import kotlin.reflect.KClass

abstract class AbstractFragment<B : ViewDataBinding, VM : AbstractViewModel> : Fragment() {

    protected lateinit var binding: B
    protected val viewModel by lazy { ViewModelProvider(this).get(getViewModelClass()) }

    //region base lifecycle methods
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // abstract fragments should only be used through pre-created abstract activity, due to dependency
        if (activity !is AbstractActivity<*, *>) {
            throw IllegalStateException("Activity has to inherit AbstractActivity!")
        }
        val layoutId = getLayoutResId()
        val bindResId = getViewModelResId()

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        var view: View?
        binding.let {
            it.setVariable(bindResId, viewModel)
            view = it.root
        }
        onPrepareLayout(view)
        return view
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    //endregion

    //region Protected methods
    protected open fun hideKeyboardFrom(view: View) {
        val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Callback after fragment's view is ready.
     *
     * @param layoutView - the layout view which is inflated
     */
    protected open fun onPrepareLayout(layoutView: View?) {
        //do nothing
    }

    //endregion

    //region public methods

    fun createShortToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun createShortToast(@StringRes stringRes: Int) {
        Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show()
    }

    //endregion

    //region navigation methods

    open fun <T : AbstractFragment<*, *>> navigateToView(clazz: KClass<T>, args: Bundle? = null) {
        activity.let {
            if (it is AbstractActivity<*, *>) {
                viewModel.postNavigationArgs()?.let { bundle ->
                    args?.putAll(bundle)
                }
                it.openView(clazz, args)
            }
        }
    }

    open fun <T : AbstractActivity<*, *>> navigateToActivity(
        clazz: KClass<T>,
        args: Bundle? = null
    ) {
        activity.let {
            if (it is AbstractActivity<*, *>) {
                it.openActivity(clazz, args)
            }
        }
    }

    fun navigateBack() {
        activity?.onBackPressed()
    }

    abstract fun getViewModelResId(): Int

    abstract fun getLayoutResId(): Int

    abstract fun getViewModelClass(): Class<VM>

    //endregion

}