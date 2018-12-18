package es.fempa.juanpomares.sockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class pantallaTexto extends MainActivity {

    boolean servidor;
    Button bEnviar, bSalir;
    EditText etTexto;
    TextView myTV;

    Socket socket;
    ServerSocket serverSocket;

    boolean ConectionEstablished;
    boolean server = false;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    int mPuerto=1048;
    //Hilo para escuchar los mensajes que le lleguen por el socket
    GetMessagesThread HiloEscucha;


    /*Variable para el servidor*/
    WaitingClientThread HiloEspera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_texto);

        bSalir = (Button)findViewById(R.id.bSalir);
        bEnviar = (Button)findViewById(R.id.bEnviar);
        etTexto = (EditText) findViewById(R.id.etTexto);
        myTV = (TextView) findViewById(R.id.tvTexto);

        bSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisconnectSockets();
            }
        });

        Bundle extras = getIntent().getExtras();

        servidor = extras.getBoolean("boolean");

        if(servidor){
            startServer();
        }else{
            startClient();
        }


    }



    public void startServer()
    {
        btncliente.setEnabled(false);
        btnservidor.setEnabled(false);
        ipServer.setEnabled(false);
        server = true;
        SetText("\nComenzamos Servidor!");
        (HiloEspera=new WaitingClientThread(this)).start();
        bEnviar.setEnabled(true);
        etTexto.setEnabled(true);
        bSalir.setEnabled(true);
        final pantallaTexto ma = this;
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                (new EnvioMensajesServidor(ma)).start();
            }
        });
    }

    public void startClient()
    {

        final pantallaTexto ma = this;
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new EnvioMensajesCliente(ma).start();

            }
        });
    }

    public void sendMessage(String txt)
    {
        new SendMessageSocketThread(txt,this).start();

    }

    public void SetText(String text)
    {
        runOnUiThread(new setUITextView(text, this));
    }

    public void AppenText(String text)
    {
        runOnUiThread(new appendUITextView(text+"\n", this));
        //runOnUiThread(new appendUITextView(text+"\n", this));
    }



    public  void vaciarChat(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etTexto.setText("");
            }
        });

    }

    public void DisconnectSockets()
    {
        if(ConectionEstablished)
        {

            ConectionEstablished = false;

            if (HiloEscucha != null)
            {
                HiloEscucha.setExecuting();
                HiloEscucha.interrupt();
                HiloEspera = null;
            }

            try {
                if (dataInputStream != null)
                    dataInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dataInputStream = null;
                try {
                    if (dataOutputStream != null)
                        dataOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dataOutputStream = null;
                    try {
                        if (socket != null)
                            socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        socket = null;
                    }
                }
            }

        }
        finish();
    }
}
