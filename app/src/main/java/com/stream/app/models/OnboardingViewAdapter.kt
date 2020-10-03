package com.stream.app.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.stream.app.R

class OnboardingViewAdapter(val layoutInflater: LayoutInflater) : PagerAdapter() {

    private val fragList = listOf(
        R.layout.fragment_global,
        R.layout.fragment_home,
        R.layout.fragment_profile
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return fragList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        return LayoutInflater.from(container.context).inflate(fragList[position], container, false).also {
//            container.addView(it)
//        }
        return layoutInflater.inflate(fragList[position], container, false).also {
            container.addView(it)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager: ViewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}