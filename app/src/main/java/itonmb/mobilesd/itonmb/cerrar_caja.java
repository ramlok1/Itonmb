package itonmb.mobilesd.itonmb;

import android.os.Bundle;
import android.widget.FrameLayout;

import itonmb.mobilesd.itonmb.Utils.BaseMenu;

public class cerrar_caja extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_cerrar_caja, contentFrameLayout);
        toolbar.setTitle("Cerrar Caja");
    }
}
