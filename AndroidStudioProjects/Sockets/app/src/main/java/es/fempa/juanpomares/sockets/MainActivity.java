package es.fempa.juanpomares.sockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity
{
    TextView myTV;
    Button btncliente, btnservidor;
    EditText ipServer;

    Socket socket;
    ServerSocket serverSocket;

    boolean ConectionEstablished;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    int mPuerto=1048;

    //Hilo para escuchar los mensajes que le lleguen por el socket
    GetMessagesThread HiloEscucha;

    /*Variable para el servidor*/
    WaitingClientThread HiloEspera;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncliente=(Button)findViewById(R.id.buttonCliente);
        btnservidor=(Button)findViewById(R.id.buttonServer);
        ipServer=(EditText) findViewById(R.id.ipServer);

        myTV=(TextView) findViewById(R.id.tvSalida);





    }

    public void startServer(View v)
    {

        Intent intent =  new Intent(this, pantallaTexto.class);
        intent.putExtra("boolean", true);
        startActivity(intent);
    }

    public void startClient(View v)
    {
        String TheIP=ipServer.getText().toString();
        if(TheIP.length()>5)
        {
            btncliente.setEnabled(false);
            btnservidor.setEnabled(false);
            ipServer.setEnabled(false);

            SetText("\nNos intentamos conectar al servidor: "+TheIP);

            (new ClientConnectToServer(TheIP, this)).start();

        }




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



    /*public void sendVariousMessages(String msgs, int time)
    {
        if(msgs!=null  &&time!=null && msgs.length==time.length)
            for (int i = 0; i < 1; i++) {
                sendMessage(msgs);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
    }*/




    //Aqui obtenemos la IP de nuestro terminal
    public String getIpAddress()
    {
        StringBuilder ip = new StringBuilder();
        try
        {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements())
            {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements())
                {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress())
                    {
                        ip.append("IP de Servidor: ").append(inetAddress.getHostAddress()).append("\n");
                    }

                }
            }
        } catch (SocketException e)
        {
            e.printStackTrace();
            ip.append("¡Algo fue mal! ").append(e.toString()).append("\n");
        }

        return ip.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //DisconnectSockets();
    }

}

