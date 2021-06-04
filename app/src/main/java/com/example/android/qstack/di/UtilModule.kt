package com.example.android.qstack.di

import android.app.Activity
import com.example.android.qstack.R
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object UtilModule {

    @Provides
    fun provideSlideRootNav(activity : Activity) : SlidingRootNavBuilder {
        return SlidingRootNavBuilder(activity).apply {
            withMenuOpened(false)
            withRootViewScale(0.85f)
            withGravity(SlideGravity.LEFT)
            withMenuLayout(R.layout.fancy_menu)
            withContentClickableWhenMenuOpened(false)
        }
    }

    @Provides
    fun providesSlide(slidingRootNavBuilder: SlidingRootNavBuilder) : SlidingRootNav {
        return slidingRootNavBuilder.inject()
    }
}