package com.example.simple2d;

import android.content.Context;
import android.graphics.*;
import android.view.View;

/**
 * Created by Huma on 09.06.2015.
 */
public class Draw2 extends View{
    private Paint mPaint = new Paint();

        public Draw2(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);

            // стиль Заливка
            mPaint.setStyle(Paint.Style.FILL);

            // закрашиваем холст белым цветом
            mPaint.setColor(Color.WHITE);
            canvas.drawPaint(mPaint);

            // Рисуем зеленый прямоугольник
            mPaint.setColor(Color.rgb(51,255,102));
            canvas.drawRect(100, 400, 500, 500, mPaint);


            Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            canvas.drawBitmap(src, 450, 530, mPaint);


            // Рисуем текст

            int x = 300;
            int y = 450;
            // Создаем ограничивающий прямоугольник для наклонного текста
            // поворачиваем холст по центру текста
            canvas.rotate(10, x , y );

            mPaint.setColor(Color.MAGENTA);
            mPaint.setTextSize(27);

            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText("Hello 2D World!", x, y, mPaint);
            // восстанавливаем холст
            canvas.restore();


        }

}
