package com.kidssaveocean.fatechanger.presentation.mvvm.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.kidssaveocean.fatechanger.presentation.AbstractViewModel
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import kotlin.reflect.KClass

private const val KEEP_IN_STACK_KEY = "keep_in_stack_key"
const val ACTIVITY_BUNDLE_EXTRA_KEY = "activity_bundle_extra_key"
private const val MAX_NUM_OF_BACK_PRESSES = 2
private const val EXIT_MSG_DELAY_TIME: Long = 2000 // time in milliseconds

abstract class AbstractActivity<B : ViewDataBinding, VM : AbstractViewModel> : AppCompatActivity() {

    private var numOfBackPressed = 0
    private var currentView: AbstractFragment<*, *>? = null

    // controls whether the back navigation should skip sending the message and execute the onBack
    private var skipBackMsg = false

    protected lateinit var binding: B
    protected val viewModel by lazy { ViewModelProvider(this).get(getViewModelClass()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        if (shouldMakeStatusBarTransparent())
            makeStatusBarTransparent()
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun getViewModelClass(): Class<VM>

    //override this if you want to make status bar transparent for current activity
    protected open fun shouldMakeStatusBarTransparent(): Boolean = false

    //region public methods

    fun createShortToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    //endregion

    //region Base methods for activities

    //todo fix deprecated stuff
    override fun onBackPressed() {
        if (currentView == null) {
            handleBackPress()
        } else {
            navigateBack()
        }
    }

    fun <T : AppCompatActivity> openActivity(activityClass: KClass<T>, args: Bundle? = null) {
        hideKeyboard()

        val resolvedArgs = resolveArgs(args, viewModel)
        //pass current activity stack request
        val keepInStack: Boolean = keepInStack()
        resolvedArgs.putBoolean(KEEP_IN_STACK_KEY, keepInStack)

        //start the new activity
        val intent = Intent(this, activityClass.java)
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA_KEY, args)
        startActivity(intent)

        //finish current activity
        if (!keepInStack) {
            finish()
        }
    }

    fun <T : AbstractFragment<*, *>> openView(viewClass: KClass<T>, args: Bundle? = null) {
        hideKeyboard()

        val containerViewId = getContainerViewId()
        if (containerViewId == 0) {
            throw RuntimeException("MISSING VIEW CONTAINER ID")
        }

        val viewName = viewClass.qualifiedName ?: return

        var currentViewClassName: String? = null
        currentView?.let {
            currentViewClassName = it::class.qualifiedName
        }
        if (currentViewClassName != viewName) {
            val fm = supportFragmentManager
            //todo this should be the intro fragment
//            if(currentView is LoginFragment){
//                fm.popBackStack()
//            }
            val existing = fm.findFragmentByTag(viewName)
            val newView: AbstractFragment<*, *>? = if (existing != null) {
                popBackStackTo(fm, viewName, args)
            } else {
                attachNewView(fm, containerViewId, viewName, args)
            }
            currentView = newView
            currentView?.let {
                onViewChanged(it::class)
            }
        }
    }

    //override this if you need to do something after every view change
    protected open fun<T: Any> onViewChanged(classz: KClass<T>) {
    }

    private fun popBackStackTo(
        fragmentManager: FragmentManager,
        viewClassName: String,
        args: Bundle?
    ): AbstractFragment<*, *>? {
        val view = fragmentManager.findFragmentByTag(viewClassName) ?: return null

        val existingArgs = view.arguments
        if (existingArgs != null && args != null) {
            existingArgs.putAll(args)
        } else {
            view.arguments = args
        }

        fragmentManager.popBackStack(viewClassName, 0)
        fragmentManager.executePendingTransactions()
        return view as AbstractFragment<*, *>
    }

    private fun attachNewView(
        fragmentManager: FragmentManager,
        containerId: Int,
        viewClassName: String,
        args: Bundle?
    ): AbstractFragment<*, *> {

        //Deprecated version of this, in case we have issues with fragmentFactory
//        val newFragment =
//            if (args != null) {
//                Fragment.instantiate(this, viewClassName, args)
//            } else {
//                Fragment.instantiate(this, viewClassName)
//            }
        val fact = fragmentManager.fragmentFactory

        val newFragment = fact.instantiate(classLoader, viewClassName)
        if (args != null) {
            newFragment.arguments = args
        }
        val ftx = fragmentManager.beginTransaction()

        ftx.replace(containerId, newFragment, viewClassName)
        ftx.addToBackStack(viewClassName)
        ftx.commit()
        fragmentManager.executePendingTransactions()

        return newFragment as AbstractFragment<*, *>
    }

    private fun navigateBack() {
        hideKeyboard()

        val fm = supportFragmentManager
        val lastBackStackEntryIndex = fm.backStackEntryCount - 1
        if (lastBackStackEntryIndex <= 0) {
            handleFinishAfterTransition()
        } else {
            var lastTaggedEntryIndex = lastBackStackEntryIndex - 1
            var backStackEntry: FragmentManager.BackStackEntry

            do {
                // search for a non-null tag for entries above the bottom one
                backStackEntry = fm.getBackStackEntryAt(lastTaggedEntryIndex)
            } while ((backStackEntry.name == null) && ((lastTaggedEntryIndex--) > 0))

            val viewClassName = backStackEntry.name
            if (viewClassName != null) {
                val newView = popBackStackTo(fm, viewClassName, null)
                currentView = newView
                currentView?.let {
                    onViewChanged(it::class)
                }
            }
        }
    }

    //handles back presses while there are fragments in the backstack of the activity
    private fun handleBackPress() {
        //todo the app has more than 1 activity... so the logic for double tap as exit will have to wait until we get rid of all of them
        super.onBackPressed()
//        ++numOfBackPressed
//        if (numOfBackPressed == MAX_NUM_OF_BACK_PRESSES || skipBackMsg) {
//
//            super.onBackPressed()
//        } else {
//            handleExitMsg()
//        }
    }

    //handles back presses while the activity has only one fragment in the backstack
    private fun handleFinishAfterTransition() {
        ++numOfBackPressed
        if (numOfBackPressed == MAX_NUM_OF_BACK_PRESSES || skipBackMsg) {
            supportFinishAfterTransition()
        } else {
            handleExitMsg()
        }
    }

    private fun handleExitMsg() {
        val exitMsg = "press again to exit"
        Toast.makeText(this, exitMsg, Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ numOfBackPressed = 0 }, EXIT_MSG_DELAY_TIME)
    }

    open fun getContainerViewId(): Int = 0

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus
        if (currentFocus != null) {
            val windowToken = currentFocus.windowToken
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    private fun resolveArgs(viewArgs: Bundle?, vm: VM): Bundle {
        val args = Bundle()
        if (viewArgs != null) {
            args.putAll(viewArgs)
        }
        vm.postNavigationArgs()?.let {
            args.putAll(it)
        }

        return args
    }

    protected open fun keepInStack(): Boolean = false
    //endregion

    private fun makeStatusBarTransparent() {
        //todo fix deprecated stuff
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.TRANSPARENT
        }
    }

    open fun isNetworkConnected(): Boolean {

        val mNetworkInfo = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable && mNetworkInfo.isConnected
        }

        return false
    }
}

