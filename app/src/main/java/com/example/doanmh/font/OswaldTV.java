package com.example.doanmh.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.doanmh.ui.Utils;

public class OswaldTV extends AppCompatTextView {
    public OswaldTV(@NonNull Context context) {
        super(context);
        setFontTextView();
    }

    public OswaldTV(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontTextView();
    }

    public OswaldTV(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontTextView();
    }
    private void setFontTextView(){
        Typeface typeface = Utils.getOswaldTypeface(getContext());
        setTypeface(typeface);
    }
}
