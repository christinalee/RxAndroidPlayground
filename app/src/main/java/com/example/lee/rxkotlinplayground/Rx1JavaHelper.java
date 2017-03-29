package com.example.lee.rxkotlinplayground;

import android.annotation.TargetApi;
import android.icu.text.DateFormat.BooleanAttribute;
import android.icu.text.IDNA.Error;
import android.os.Build.VERSION_CODES;
import android.util.Log;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lee on 7/22/16.
 */
class CustomRxError extends java.lang.Error {
    public CustomRxError(String message) {
        super(message);
    }
}

public class Rx1JavaHelper {
   private static final String TAG = "Rx1JavaHelper";

    public static void observableJust() {
        //An example of using a single action instead of the full trio
        Observable.just("Hello World!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "onNext(" + s + ")");
                    }
                });
   }

   public static void basicNameObservable(String[] names) {
       Observable<String> obs = Observable.from(names);
       logOutput(obs);
   }

    public static void observableCreate() {
        Observable<String> obs = Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("I created an Observable!");
                subscriber.onCompleted();
            }
        });

        logOutput(obs);
    }

    public static Object functionThatErrors() {
        throw new CustomRxError("Some random error");
    }

    public static void errorObservable() {
        Observable<Object> obs = Observable.create(
                new OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        try {
                            Object result = functionThatErrors();
                            subscriber.onNext(result);
                        } catch (Exception e){
                            subscriber.onError(e);
                        }
                        subscriber.onCompleted();
                        subscriber.onNext("I created an Observable!");
                        subscriber.onCompleted();
                    }

                }
        );
        logOutput(obs);
    }

    public static void mapObservable() {
        Integer[] numbers = {1, 2, 3, 4};
        Observable<Integer> obs = Observable.from(numbers).map(
                new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer + 1;
                    }
                }
        );

        logOutput(obs);
    }

    public  static void filterObservable() {
        Integer[] numbers = {1, 2, 3, 4};
        Observable<Integer> obs = Observable.from(numbers).filter(
                new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                }
        );

        logOutput(obs);
    }

    private static <T> void logOutput(Observable<T> obs) {
        obs.subscribe(new Subscriber<T>() {
            @Override public void onCompleted() {
                Log.i(TAG, "onCompleted()");
            }

           @Override public void onError(Throwable e) {
               Log.i(TAG, "onError(" + e.getMessage() + ")");
           }

           @Override public void onNext(T result) {
               Log.i(TAG, "onNext(" + result + ")");
           }
       });
    }
}
