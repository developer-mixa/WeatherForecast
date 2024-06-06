package com.example.weatherforecast.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.weatherforecast.R

fun FragmentActivity.launchFragment(fragment: Fragment, @IdRes idFragment: Int = R.id.fragmentMainContainer, addToBackStack: Boolean = false){
    val transaction = supportFragmentManager.beginTransaction()

    if (addToBackStack) {
        transaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right);
        transaction.addToBackStack(null)
    }

    transaction.replace(idFragment, fragment).commit()
}



