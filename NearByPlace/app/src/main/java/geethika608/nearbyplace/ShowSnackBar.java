package geethika608.nearbyplace;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by geethika on 20/05/16.
 */
public class ShowSnackBar {

    Snackbar snackbar;

    public ShowSnackBar(View view, String msg) {

         snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(Color.WHITE);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(Color.parseColor("#212121"));
        snackbar.show();
    }
}
