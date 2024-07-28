package com.example.doanmh.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.doanmh.ui.Utils;

public class PlayfairTV extends AppCompatTextView {
    public PlayfairTV(@NonNull Context context) {
        super(context);
        setFontTextView();
    }

    public PlayfairTV(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontTextView();
    }

    public PlayfairTV(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontTextView();
    }
    private void setFontTextView(){
        Typeface typeface = Utils.getPlayfairTypeface(getContext());
        setTypeface(typeface);
    }
}
