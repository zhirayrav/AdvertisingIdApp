package com.example.myfirstapp


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.ads.identifier.AdvertisingIdClient
import androidx.ads.identifier.AdvertisingIdInfo
import androidx.appcompat.app.AppCompatActivity
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures.addCallback
import java.util.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private var zd : TimeZone = TimeZone.getDefault()
    private var textViewTimeZone: TextView ? = null
    private var textViewId: TextView ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewTimeZone = findViewById(R.id.textViewTimeZone)
        textViewId = findViewById(R.id.textViewId)
        getTimeZone()
        val isProviderAvailable: Boolean = AdvertisingIdClient.isAdvertisingIdProviderAvailable(this)
        Log.i("Provider is available: ", isProviderAvailable.toString())
        val x = Runnable { run { determineAdvertisingInfo() } }
        x.run()
    }
    fun getTimeZone() {
        textViewTimeZone?.text = TimeZone.getDefault().id
    }

    private fun determineAdvertisingInfo() {
            val advertisingIdInfoListenableFuture =
                AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)

            addCallback(advertisingIdInfoListenableFuture,
                object : FutureCallback<AdvertisingIdInfo> {
                    override fun onSuccess(adInfo: AdvertisingIdInfo?) {
                        val id: String? = adInfo?.id
                        val providerPackageName: String? = adInfo?.providerPackageName
                        val isLimitTrackingEnabled: Boolean? = adInfo?.isLimitAdTrackingEnabled
                        textViewId?.text = id
                    }

                    // Any exceptions thrown by getAdvertisingIdInfo()
                    // cause this method to be called.
                    override fun onFailure(t: Throwable) {
                        textViewId?.text = "Fail"
                        Log.e("MY_APP_TAG",
                            "Failed to connect to Advertising ID provider.")
                    }
                }, Executors.newSingleThreadExecutor())
        }
    }
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val isProviderAvailable: Boolean = AdvertisingIdClient.isAdvertisingIdProviderAvailable(this)
//        Log.i("Provider is available: ", isProviderAvailable.toString())
//        val x = Runnable { run { determineAdvertisingInfo() } }
//        x.run()
//    }
//    private fun determineAdvertisingInfo() {
//        val advertisingIdInfoListenableFuture =
//            AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
//        addCallback(
//            advertisingIdInfoListenableFuture,
//            object : FutureCallback<AdvertisingIdInfo> {
//                override fun onSuccess(adInfo: AdvertisingIdInfo?) {
//                    val id: String? = adInfo?.id
//                    val providerPackageName: String? = adInfo?.providerPackageName
//                    val isLimitTrackingEnabled: Boolean? = adInfo?.isLimitAdTrackingEnabled
//                }
//                override fun onFailure(t: Throwable) {
//                    Log.e("=====", "Failed to connect to Advertising ID provider.")
//                }
//            }, MoreExecutors.directExecutor()
//        )
//    }


