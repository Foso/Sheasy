/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.jensklingenberg.sheasy.data.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.VisibleForTesting
import android.support.v4.app.FragmentActivity
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.api.ChangeableBaseUrlInterceptor
import de.jensklingenberg.sheasy.network.api.SheasyAPI
import javax.inject.Inject


class ViewModelFactory private constructor() :
    ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var application: Application


    @Inject
    lateinit var hostSelectionInterceptor: ChangeableBaseUrlInterceptor

    @Inject
    lateinit var retrofit: SheasyAPI

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommonViewModel::class.java)) {

            return CommonViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(NetworkViewModel::class.java)) {

            return NetworkViewModel(application, hostSelectionInterceptor, retrofit) as T
        }

        if (modelClass.isAssignableFrom(ShareScreenViewModel::class.java)) {

            return ShareScreenViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(PermissionViewModel::class.java)) {

            return PermissionViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(AppsViewModel::class.java)) {

            return AppsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {

            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory()
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }

        fun obtainProfileViewModel(activity: FragmentActivity?): CommonViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity!!.application)
            return ViewModelProviders.of(activity!!, factory).get(CommonViewModel::class.java)
        }

        fun obtainNetworkViewModel(activity: FragmentActivity?): NetworkViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity!!.application)
            return ViewModelProviders.of(activity!!, factory).get(NetworkViewModel::class.java)
        }

        fun obtainPermissionViewModel(activity: FragmentActivity?): PermissionViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity!!.application)
            return ViewModelProviders.of(activity!!, factory).get(PermissionViewModel::class.java)
        }

        fun obtainShareScreenViewModel(activity: FragmentActivity?): ShareScreenViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity!!.application)
            return ViewModelProviders.of(activity!!, factory).get(ShareScreenViewModel::class.java)
        }

        fun obtainAppsViewModel(activity: FragmentActivity?): AppsViewModel {
            val factory = ViewModelFactory.getInstance(activity!!.application)
            return ViewModelProviders.of(activity!!, factory).get(AppsViewModel::class.java)
        }
    }


}
