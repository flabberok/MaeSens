package by.maesens.android.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import by.maesens.android.R;
import by.maesens.android.utils.BitmapConverter;

/**
 * Created by Igor Paliashchuk on 14.03.16.
 * View for sharing auction information with VK social network.
 * It contains icon and number of reposts.
 */
public class VKView extends TextView {

    private Bitmap mIcon;

    public VKView(Context context) {
        super(context);
        init();
    }

    public VKView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VKView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Drawable logoDrawable;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            logoDrawable = getResources().getDrawable(R.drawable.ic_vk, null);
        else
            logoDrawable = getResources().getDrawable(R.drawable.ic_vk);
        mIcon = BitmapConverter.drawableToBitmap(logoDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        RectF rectFull = new RectF(0, 0, getWidth(), getHeight());
        RectF rectHalf = new RectF(0, 0, getWidth() / 2, getHeight());

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.WHITE);
        canvas.drawRect(rectFull, paint);

        paint.setColor(Color.rgb(230, 230, 250));
        canvas.drawRect(rectHalf, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorBorderElementsDark));
        paint.setStrokeWidth(3);
        canvas.drawRect(rectFull, paint);

        float scale = getResources().getDisplayMetrics().density;
        canvas.drawBitmap(mIcon, 8f * scale, 4f * scale, paint);

        super.onDraw(canvas);
    }
}
