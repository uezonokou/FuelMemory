package com.example.fuelmemory;

import android.app.AppComponentFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends View {

    public MainMenu(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setTextSize(62);
        paint.setColor(Color.rgb(0,0,0));

        canvas.drawText("燃費記録アプリ",0,80,paint);

    }
}
