package com.stream.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.stream.app.R
import com.stream.app.models.OnboardingViewAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnboardingActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var dots: WormDotsIndicator
    lateinit var viewAdapter: OnboardingViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.vp_onboarding)
        dots = findViewById(R.id.dots_onboarding)
        viewAdapter = OnboardingViewAdapter(layoutInflater)

        viewPager.adapter = viewAdapter
        dots.setViewPager(viewPager)
    }
}
