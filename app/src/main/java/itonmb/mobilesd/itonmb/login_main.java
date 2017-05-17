package itonmb.mobilesd.itonmb;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.SQLException;

public class login_main extends AppCompatActivity {

    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        btn_login = (Button) findViewById(R.id.btn_login_entrar);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent =
                                        new Intent(getApplicationContext(), apertura_caja.class);
                                startActivity(intent);
                            }
                        });
                    }
                }).start();

            }
        });


    }
}
