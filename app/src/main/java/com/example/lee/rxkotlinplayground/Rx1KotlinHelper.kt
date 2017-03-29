package com.example.lee.rxkotlinplayground

import android.util.Log
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by lee on 7/22/16.
 *
 * Add methods to experiment with RxJava1 behavior here :)
 */

class Rx1KotlinHelper {
    companion object {
        private val TAG: String = "Rx1KotlinHelper"

        fun observableJust() {
            //Example of an observable with only one action
            val obs = Observable.just("Hello World!")
            logOutput(obs)
        }

        fun observableCreate() {
            val obs: Observable<String> = Observable.create<String> { s ->
                s.onNext("I created an observable!")
                s.onCompleted()
            }
            logOutput(obs)
        }

        fun functionThatErrors(): String {
            throw Error("Some Random Error")
        }

        fun errorObservable() {
            val obs: Observable<String> = Observable.create<String> { s ->
                try {
                    val result = functionThatErrors()
                    s.onNext(result)
                } catch (e: Error){
                    s.onError(e)
                }
                s.onCompleted()
            }
            logOutput(obs)
        }

        fun basicNameObservable(names: Array<String>) {
            val obs: Observable<String> = Observable.from(names)
            logOutput(obs)
        }

        fun cancelingSubscription() {
            val obs: Observable<Int> = Observable
                    .from(arrayOf("some", "list", "of", "words"))
                    .flatMap { word ->
                        Observable.timer(3, TimeUnit.MICROSECONDS).map { word.length }
                    }.doOnNext { length ->
                        Log.i(TAG, "length: $length")
                    }

            val sub: Subscription = obs.subscribe{ next ->
                Log.i(TAG, "onNext($next)")
            }
            sub.unsubscribe()
        }

        fun mergeObservable() {
            val schoolFriendsObs = Observable.from(arrayOf("Mo", "Dave"))
            val workFriendsObs = Observable.from(arrayOf("Nicole", "Alison"))

            val allFriendsObs = Observable.merge(schoolFriendsObs, workFriendsObs)

            logOutput(allFriendsObs)
        }

       fun threadingObservable() {
            Observable.from(arrayOf("Red", "Orange", "Blue")).doOnNext { color ->
                Log.i(TAG, "Color $color pushed through on ${Thread.currentThread()}")
            }.observeOn(Schedulers.io()).map { color ->
                color.length
            }.subscribe { length ->
                Log.i(TAG, "Length $length being received on ${Thread.currentThread()}")
            }
        }

       fun threadingObservable2() {
            Observable.from(arrayOf("Red", "Orange", "Blue"))
                    .doOnNext { color ->
                        Log.i(TAG, "Color $color pushed through on ${Thread.currentThread()}")
                    }.observeOn(Schedulers.io())
                    .map { color -> color.length }
                    .subscribeOn(Schedulers.computation())
                    .subscribe { length ->
                        Log.i(TAG, "Length $length being recieved on ${Thread.currentThread()}")
                    }
       }

       fun mapObservable() {
           val intArr: Array<Int> = arrayOf(1, 2, 3, 4)
           val obs: Observable<Int> = Observable.from(intArr).map{ i ->
               i + 1
           }

           logOutput(obs)
       }

       fun filterObservable() {
           val intArr: Array<Int> = arrayOf(1, 2, 3, 4)
           val obs: Observable<Int> = Observable.from(intArr).filter{ i ->
               i % 2 == 0
           }

           logOutput(obs)
       }

        fun takeUntilObs() {
            val first = Observable.timer(5, TimeUnit.SECONDS).repeat().map { it -> 1 }
            val second = Observable.timer(2, TimeUnit.SECONDS).map { it -> 2 }

            val takeUntil = first.takeUntil(second)
            val merge = takeUntil.mergeWith(second)

            logOutput(merge)
        }

        fun <T> logOutput(obs: Observable<T>) {
            obs.subscribe(
                    { next ->
                        Log.i(TAG, "onNext($next)")
                    },
                    { error ->
                        Log.i(TAG, "onError($error)")
                    },
                    {
                        Log.i(TAG, "onComplete()")
                    }
            )
        }
    }
}
