package geethika608.nearbyplace;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by sky on 24/8/16.
 */
public class ViewToImageConverter {

    public ViewToImageConverter() {
    }


    public static Bitmap Convert(View view) {

        view.setDrawingCacheEnabled(true);
        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false); // clear drawing cache

        return b;
    }

}
