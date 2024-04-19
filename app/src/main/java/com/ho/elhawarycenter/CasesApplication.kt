package com.ho.elhawarycenter

import android.app.Application
import com.google.android.material.color.DynamicColors

class CasesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}