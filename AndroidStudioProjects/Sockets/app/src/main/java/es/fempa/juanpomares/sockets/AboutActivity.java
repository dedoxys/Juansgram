package es.fempa.juanpomares.sockets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        mytoolbar = findViewById(R.id.toolbar);
        mytoolbar.setTitle("Información");
        setSupportActionBar(mytoolbar);



        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mytoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

                mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();

                    }
                });
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu _menu){
        getMenuInflater().inflate(R.menu.menuabout, _menu);
        return true;
    }

}
