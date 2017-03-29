package com.example.lee.rxkotlinplayground

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import java.util.concurrent.TimeUnit

/**
 * Created by lee on 3/29/17.
 *
 * Add methods to experiment with RxJava2 behavior here :)
 */

class Rx2KotlinHelper {
    companion object {
        private val TAG: String = "Rx2KotlinHelper"

        fun observableJust() {
            //Example of an observable with only one action
            val obs = Observable.just("Hello World!")
            logOutput(obs)
        }

        fun observableCreate() {
            val obs: Observable<String> = Observable.create<String> { s ->
                s.onNext("I created an observable!")
                s.onComplete()
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
                s.onComplete()
            }
            logOutput(obs)
        }

        fun basicNameObservable(names: Array<String>) {
            val obs: Flowable<String> = Flowable.fromIterable(names.toMutableList())
            logOutput(obs)
        }

        fun cancelingSubscription() {
            val obs: Flowable<Int> = Flowable
                    .fromIterable(mutableListOf("some", "list", "of", "words"))
                    .flatMap { word ->
                        Flowable.timer(3, TimeUnit.MICROSECONDS).map { word.length }
                    }.doOnNext { length ->
                Log.i(TAG, "length: $length")
            }

            val sub: ResourceSubscriber<Int> = object : ResourceSubscriber<Int>() {
                override fun onNext(t: Int?) {
                    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onComplete() {
                    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(t: Throwable?) {
                    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
            val disp: Disposable = obs.subscribeWith(sub)
            sub.dispose()
        }

        fun mergeObservable() {
            val schoolFriendsObs = Observable.fromIterable(mutableListOf("Mo", "Dave"))
            val workFriendsObs = Observable.fromIterable(mutableListOf("Nicole", "Alison"))

            val allFriendsObs = Observable.merge(schoolFriendsObs, workFriendsObs)

            logOutput(allFriendsObs)
        }

        fun threadingObservable() {
            Observable.fromIterable(mutableListOf("Red", "Orange", "Blue"))
                    .doOnNext { color ->
                        Log.i(TAG, "Color $color pushed through on ${Thread.currentThread()}")
                    }
                    .observeOn(Schedulers.io())
                    .map(String::length)
                    .subscribe { length ->
                        Log.i(TAG, "Length $length being received on ${Thread.currentThread()}")
                    }
        }

        fun threadingObservable2() {
            Observable.fromIterable(mutableListOf("Red", "Orange", "Blue"))
                    .doOnNext { color ->
                        Log.i(TAG, "Color $color pushed through on ${Thread.currentThread()}")
                    }.observeOn(Schedulers.io())
                    .map(String::length)
                    .subscribeOn(Schedulers.computation())
                    .subscribe { length ->
                        Log.i(TAG, "Length $length being received on ${Thread.currentThread()}")
                    }
        }

        fun mapObservable() {
            val intArr: MutableList<Int> = mutableListOf(1, 2, 3, 4)
            val obs: Observable<Int> = Observable.fromIterable(intArr).map{ i ->
                i + 1
            }

            logOutput(obs)
        }

        fun filterObservable() {
            val intArr: MutableList<Int> = mutableListOf(1, 2, 3, 4)
            val obs: Observable<Int> = Observable.fromIterable(intArr).filter{ i ->
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

        fun <F, T> logOutput(obs: F) where F : Flowable<T> {
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

        fun <O, T> logOutput(obs: O) where O : Observable<T> {
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
