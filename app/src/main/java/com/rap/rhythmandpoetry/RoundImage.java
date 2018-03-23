package com.rap.rhythmandpoetry;

/* Round Image - this file takes a bitmap image and converts it to a circular view by
drawing a circular canvas and placing the image inside the canvas bounds
*/


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class RoundImage extends Drawable {
    private final Bitmap Bitmap;
    private final Paint Paint;
    private final RectF Rect;
    private final int Width;
    private final int Height;

    /* Constructor
    Parameters : Bitmap
    * */
    public RoundImage(Bitmap bitmap) {
        Bitmap = bitmap;
        Rect = new RectF();
        Paint = new Paint();
        Paint.setAntiAlias(true);
        Paint.setDither(true);
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint.setShader(shader);
        Width = Bitmap.getWidth();
        Height = Bitmap.getHeight();
    }

    /*drawing the canvas for which the image will sit on
    Parameters:
    Rectf : android widget with four float values used to create rectangle
    Paint: style and color info for canvas
    * */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(Rect, Paint);
    }


    /*
    setting the bounds of the rectangle
    * */
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        Rect.set(bounds);
    }

    /*
    setting the transparency of the color
    Parameters:
    Integer alpha( value between 0 -255 )
    alpha is used to determine what the opacity of the color will be.
    * */
    @Override
    public void setAlpha(int alpha) {
        if (Paint.getAlpha() != alpha) {
            Paint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    /*
    adding a filter to the color
    * */
    @Override
    public void setColorFilter(ColorFilter cf) {
        Paint.setColorFilter(cf);
    }


    /*
    Returns the opacity of the the rect object
   * */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /*
    returns the width of rect
    * */
    @Override
    public int getIntrinsicWidth() {
        return Width;
    }

    /*
    returns height of rect
    * */
    @Override
    public int getIntrinsicHeight() {
        return Height;
    }


    /*
    function for smoothing the edges of the the Paint object
    Parameters:
    Boolean aa
    * */
    public void setAntiAlias(boolean aa) {
        Paint.setAntiAlias(aa);
        invalidateSelf();
    }


    @Override
    public void setFilterBitmap(boolean filter) {
        Paint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        Paint.setDither(dither);
        invalidateSelf();
    }

    /*
    Parameters:
    Bitmap
    Returns the bitmap version of image
    * */
    public Bitmap getBitmap() {
        return Bitmap;
    }

}

