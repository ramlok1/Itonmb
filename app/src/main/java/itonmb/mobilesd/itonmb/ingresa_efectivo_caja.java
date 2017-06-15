package itonmb.mobilesd.itonmb;

import android.os.Bundle;
import android.widget.FrameLayout;

import itonmb.mobilesd.itonmb.Utils.BaseMenu;

public class ingresa_efectivo_caja extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_ingresa_efectivo_caja, contentFrameLayout);
        toolbar.setTitle("Ingresa efectivo a Caja");
    }
}
