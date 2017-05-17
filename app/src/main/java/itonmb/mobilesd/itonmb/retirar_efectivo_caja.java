package itonmb.mobilesd.itonmb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class retirar_efectivo_caja extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_retirar_efectivo_caja, contentFrameLayout);
        toolbar.setTitle("Retirar efectivo de Caja");
    }
}
