package itonmb.mobilesd.itonmb.Utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;



/**
 * Created by Conrado_Mobiles on 01/06/2017.
 */

public class Snackmsg {



    public Snackbar getBar(View v, String texto, int icono, String color){
        Snackbar bar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
        bar.getView().setBackgroundColor(Color.parseColor(color));
        TextView tv = (TextView) bar.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setText(texto);
        tv.setTextSize(20);
        tv.setCompoundDrawablesWithIntrinsicBounds(icono, 0, 0, 0);

        return bar;

    }
}
