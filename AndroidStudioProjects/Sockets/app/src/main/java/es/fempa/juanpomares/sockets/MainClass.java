package es.fempa.juanpomares.sockets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar my_Toolbar = findViewById(R.id.miToolbar);
        setSupportActionBar(my_Toolbar);

        Button btnServerIniciar = findViewById(R.id.buttonServer);
        Button btnClienteIniciar = findViewById(R.id.buttonCliente);
        final EditText editText = findViewById(R.id.ipServer);
        final EditText etnombre =  findViewById(R.id.etNombre);
        btnServerIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etnombre.getText().toString().equals("")){
                    Intent intent = new Intent(MainClass.this, pantallaText.class);
                    intent.putExtra("QuienSoy", "server");
                    intent.putExtra("nombre", etnombre.getText().toString());
                    intent.putExtra("ip",editText.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(MainClass.this,"Debe introducirse nombre",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnClienteIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etnombre.getText().toString().equals("")){
                    Intent intent = new Intent(MainClass.this, pantallaText.class);
                    intent.putExtra("QuienSoy", "cliente");
                    intent.putExtra("nombre", etnombre.getText().toString());
                    if ((editText.getText().toString().equals(""))){
                        String ip = "0";
                        intent.putExtra("ip",ip);
                    }else
                        intent.putExtra("ip",editText.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(MainClass.this,"Debe introducirse nombre",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuinfo,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.acercaDe:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;

        }
        return false;
    }
}
