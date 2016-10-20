package com.grandmagic.edustore.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhangmengqi on 2016/9/13.
 */
public class TextViewWithFocus extends TextView {

    public TextViewWithFocus(Context context) {
        super(context);
    }

    public TextViewWithFocus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewWithFocus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
