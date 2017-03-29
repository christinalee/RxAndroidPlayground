package com.example.lee.rxkotlinplayground;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by lee on 3/29/17.
 */

public class Rx2JavaHelper {
   private static final String TAG = "Rx1JavaHelper";

    public static void observableJust() {
        //An example of using a single action instead of the full trio
        Observable.just("Hello World!")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.i(TAG, "onNext(" + s + ")");
                    }
                });
   }

   public static void basicNameObservable(String[] names) {
       Flowable<String> obs = Flowable.fromArray(names);
       logOutput(obs);
   }

    public static void observableCreate() {
        Flowable<String> obs = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("I created an Observable!");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        logOutput(obs);
    }

    private static Object functionThatErrors() {
        throw new CustomRxError("Some random error");
    }

    public static void errorObservable() {
        Observable<Object> obs = Observable.create(
                new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        try {
                            Object result = functionThatErrors();
                            e.onNext(result);
                        } catch (Exception ex){
                            e.onError(ex);
                        }
                        e.onNext("I created an Observable!");
                        e.onComplete();
                    }
                }
        );
        logOutput(obs);
    }

    public static void mapObservable() {
        Integer[] numbers = {1, 2, 3, 4};
        Observable<Integer> obs = Observable.fromArray(numbers).map(
                new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer + 1;
                    }
                }
        );

        logOutput(obs);
    }

    public  static void filterObservable() {
        Integer[] numbers = {1, 2, 3, 4};
        Observable<Integer> obs = Observable.fromArray(numbers).filter(
                new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                }
        );

        logOutput(obs);
    }

    private static <T> void logOutput(Observable<T> obs) {
        obs.subscribe(new Observer<T>() {
            @Override public void onError(Throwable e) {
                Log.i(TAG, "onError(" + e.getMessage() + ")");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onCompleted()");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override public void onNext(T result) {
                Log.i(TAG, "onNext(" + result + ")");
            }
       });
    }

    private static <T> void logOutput(Flowable<T> flow) {
        flow.subscribe(new Subscriber<T>() {
            @Override public void onError(Throwable e) {
                Log.i(TAG, "onError(" + e.getMessage() + ")");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onCompleted()");
            }

            @Override
            public void onSubscribe(Subscription s) {
                Log.i(TAG, "onSubscribe");
            }

            @Override public void onNext(T result) {
                Log.i(TAG, "onNext(" + result + ")");
            }
       });
    }
}