package com.loopeer.pulltorefresh.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.loopeer.pulltorefresh.R;
import com.loopeer.pulltorefresh.view.StartUpPtrHeader;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MainActivity extends AppCompatActivity {
    private PtrFrameLayout mPtrFrameLayout;
    private StartUpPtrHeader mStartUpPtrHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.pull_refresh);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.refreshComplete();
                    }
                }, 3000);
            }
        });

        mStartUpPtrHeader = new StartUpPtrHeader(this);
        mPtrFrameLayout.setHeaderView(mStartUpPtrHeader);
        mPtrFrameLayout.addPtrUIHandler(mStartUpPtrHeader);
    }
}
