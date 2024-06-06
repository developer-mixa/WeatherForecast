package com.example.weatherforecast.presentation.navigation

import android.app.Application
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.example.navigation.BaseScreen
import com.example.navigation.MainActivityActions
import com.example.navigation.Navigator
import com.example.weatherforecast.presentation.activities.MainActivity
import com.example.weatherforecast.R

const val ARG_SCREEN = "SCREEN"

class MainNavigator(
    application: Application
): AndroidViewModel(application), Navigator {

    val whenActivityActive = MainActivityActions()

    override fun launch(screen: BaseScreen, addToBackStack: Boolean) = whenActivityActive{
        launchFragment(it as MainActivity, screen, addToBackStack, R.id.fragmentMainContainer)
    }


    override fun goBack(result: Any?) = whenActivityActive{
        it.onBackPressedDispatcher.onBackPressed()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_LONG).show()
    }

    override fun toast(messageString: String) {
        Toast.makeText(getApplication(), messageString, Toast.LENGTH_LONG).show()
    }

    override fun activityScope(block: (AppCompatActivity) -> Unit) = whenActivityActive{
        block(it)
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = false, @IdRes idFragment: Int = R.id.fragmentMainContainer){
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            transaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right);
            transaction.addToBackStack(null)
        }

        transaction.replace(idFragment, fragment).commit()
    }

}