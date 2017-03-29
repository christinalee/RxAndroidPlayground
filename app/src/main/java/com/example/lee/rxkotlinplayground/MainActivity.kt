package com.example.lee.rxkotlinplayground

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Functions to test RxJava in Java
        executeRx1Java()
        executeRx2Java()

        //Functions to test RxJava in Kotlin
        executeRx1Kotlin()
        executeRx2Kotlin()
    }

    fun executeRx1Java() {
        Log.i(TAG, "---Observable.just---")
        Rx1JavaHelper.observableJust()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.create---")
        Rx1JavaHelper.observableCreate()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.create with Error---")
        Rx1JavaHelper.errorObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.from---")
        val names = arrayOf("Christina", "Nicole", "Alison")
        Rx1JavaHelper.basicNameObservable(names)
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.map---")
        Rx1JavaHelper.mapObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.filter---")
        Rx1JavaHelper.filterObservable()
        Log.i(TAG, "\n\n")
    }

    fun executeRx2Java() {
        Log.i(TAG, "---Observable.just---")
        Rx2JavaHelper.observableJust()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Flowable.create---")
        Rx2JavaHelper.observableCreate()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.create with Error---")
        Rx2JavaHelper.errorObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Flowable.from---")
        val names = arrayOf("Christina", "Nicole", "Alison")
        Rx2JavaHelper.basicNameObservable(names)
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.map---")
        Rx2JavaHelper.mapObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.filter---")
        Rx2JavaHelper.filterObservable()
        Log.i(TAG, "\n\n")
    }

    fun executeRx1Kotlin() {
        Log.i(TAG, "---Observable.just---")
        Rx2KotlinHelper.observableJust()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.create---")
        Rx2KotlinHelper.observableCreate()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.create with Error---")
        Rx1KotlinHelper.errorObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.from---")
        val names = arrayOf("Christina", "Nicole", "Alison")
        Rx1KotlinHelper.basicNameObservable(names)
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.map---")
        Rx1KotlinHelper.mapObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.filter---")
        Rx1KotlinHelper.filterObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Canceling Subscription---")
        Rx1KotlinHelper.cancelingSubscription()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---ObserveOn Example---")
        Rx1KotlinHelper.threadingObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---ObserveOn + SubscribeOn Example---")
        Rx1KotlinHelper.threadingObservable2()

        Log.i(TAG, "---Merge Observable Example---")
        Rx1KotlinHelper.mergeObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---TakeUntil ---")
        Rx1KotlinHelper.takeUntilObs()
        Log.i(TAG, "\n\n")
    }

    fun executeRx2Kotlin() {
        Log.i(TAG, "---Observable.just---")
        Rx2KotlinHelper.observableJust()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.create---")
        Rx2KotlinHelper.observableCreate()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.create with Error---")
        Rx2KotlinHelper.errorObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Flowable.from---")
        val names = arrayOf("Christina", "Nicole", "Alison")
        Rx2KotlinHelper.basicNameObservable(names)
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.map---")
        Rx2KotlinHelper.mapObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Observable.filter---")
        Rx2KotlinHelper.filterObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---Canceling Subscription---")
        Rx2KotlinHelper.cancelingSubscription()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---ObserveOn Example---")
        Rx2KotlinHelper.threadingObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---ObserveOn + SubscribeOn Example---")
        Rx2KotlinHelper.threadingObservable2()

        Log.i(TAG, "---Merge Observable Example---")
        Rx2KotlinHelper.mergeObservable()
        Log.i(TAG, "\n\n")

        Log.i(TAG, "---TakeUntil ---")
        Rx2KotlinHelper.takeUntilObs()
        Log.i(TAG, "\n\n")
    }
}
