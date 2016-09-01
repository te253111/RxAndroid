package com.example.cylonz.rxandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt1);
        //this.dosomething();
        this.dosomething2();

    }


    public String getNameList(int index) {
        List<String> nameList = Arrays.asList("Cupcake",
                "Donut",
                "Eclair",
                "Froyo",
                "Gingerbread",
                "Honeycomb",
                "Ice Cream Sandwich",
                "Jelly Bean",
                "Kitkat",
                "Lollipop",
                "Marshmallow",
                "Nugat");
        return nameList.get(index);
    }

    public String getUserId() throws InterruptedException {
        Thread.sleep(5000);
        return "1234";
    }

    private void dosomething(){
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getNameList(0);
            }
        }).subscribeOn(Schedulers.io())
          .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("Rx","Complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Rx","Error");
            }

            @Override
            public void onNext(String s) {
                Log.d("Rx","name"+s);
                txt.setText("Hello : "+s);
            }
        });
    }

    private void dosomething2(){
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getUserId();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Rx","Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Rx","Error "+e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("Rx","Next");
                        txt.setText("Hello : "+s);
                    }
                });
    }
}
