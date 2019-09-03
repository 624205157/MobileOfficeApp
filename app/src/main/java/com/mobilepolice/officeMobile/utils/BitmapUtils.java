package com.mobilepolice.officeMobile.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapUtils {
    /**
     * 图片合成 之 圆角矩形
     *
     * @param src    源图片
     * @param radius 圆角半径
     * @return 圆角图片
     */
    public static Bitmap ovalRectangleBitmap(Bitmap src, float radius) {
        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_4444, true);
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(dest);

        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.right = bitmap.getWidth();
        rectF.top = 0;
        rectF.bottom = bitmap.getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        canvas.save();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        canvas.save();
        bitmap.recycle();
        return dest;
    }
}
