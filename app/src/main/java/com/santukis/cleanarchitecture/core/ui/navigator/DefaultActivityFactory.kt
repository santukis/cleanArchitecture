package com.santukis.cleanarchitecture.core.ui.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.santukis.navigator.ActivityFactory

class DefaultActivityFactory(private val activity: Class<out Activity>): ActivityFactory() {

    override fun getIntent(context: Context): Intent = Intent(context, activity)
}