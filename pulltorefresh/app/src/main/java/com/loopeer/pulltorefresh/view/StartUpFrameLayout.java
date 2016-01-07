package com.loopeer.pulltorefresh.view;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class StartUpFrameLayout extends PtrFrameLayout {

    private StartUpPtrHeader mStartUpPtrHeader;

    public StartUpFrameLayout(Context context) {
        this(context, null);
    }

    public StartUpFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartUpFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mStartUpPtrHeader = new StartUpPtrHeader(getContext());
        setHeaderView(mStartUpPtrHeader);
        addPtrUIHandler(mStartUpPtrHeader);
    }
}
