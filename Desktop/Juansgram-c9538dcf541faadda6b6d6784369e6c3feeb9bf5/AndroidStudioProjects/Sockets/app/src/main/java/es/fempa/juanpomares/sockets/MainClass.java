package es.fempa.juanpomares.sockets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnServerIniciar = (Button)findViewById(R.id.buttonServer);
        Button btnClienteIniciar = (Button)findViewById(R.id.buttonCliente);
        final EditText editText = (EditText)findViewById(R.id.ipServer);
        btnServerIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainClass.this, pantallaText.class);
                intent.putExtra("QuienSoy", "server");
                intent.putExtra("ip",editText.getText().toString());
                startActivity(intent);
            }
        });

        btnClienteIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainClass.this, pantallaText.class);
                intent.putExtra("QuienSoy", "cliente");
                intent.putExtra("ip",editText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
