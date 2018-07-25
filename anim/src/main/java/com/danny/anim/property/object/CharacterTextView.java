package com.danny.anim.property.object;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 接收Character的TextView
 * Created by danny on 18-7-25.
 */

public class CharacterTextView extends TextView {
    public CharacterTextView(Context context) {
        super(context);
    }

    public CharacterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CharacterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCharText(Character character){
        setText(String.valueOf(character));
    }
}
