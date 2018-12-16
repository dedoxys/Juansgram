package es.fempa.juanpomares.sockets;

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
    Button btncliente, btnservidor,bEnviar, bSalir;
    EditText ipServer, etTexto;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncliente=(Button)findViewById(R.id.buttonCliente);
        btnservidor=(Button)findViewById(R.id.buttonServer);
        ipServer=(EditText) findViewById(R.id.ipServer);
        bSalir = (Button)findViewById(R.id.bCerrar);

        myTV=(TextView) findViewById(R.id.tvSalida);

        bEnviar = (Button)findViewById(R.id.bEnviar);
        bEnviar.setEnabled(false);
        etTexto = (EditText) findViewById(R.id.etTexto);
        etTexto.setEnabled(false);

        bSalir.setEnabled(false);

        bSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisconnectSockets();
            }
        });



    }

    public void startServer(View v)
    {
        btncliente.setEnabled(false);
        btnservidor.setEnabled(false);
        ipServer.setEnabled(false);
        server = true;
        SetText("\nComenzamos Servidor!");
        (HiloEspera=new WaitingClientThread()).start();
        bEnviar.setEnabled(true);
        etTexto.setEnabled(true);
        bSalir.setEnabled(true);
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    (new EnvioMensajesServidor()).start();
            }
        });
    }

    public void startClient(View v)
    {
        String TheIP=ipServer.getText().toString();
        if(TheIP.length()>5)
        {
            btncliente.setEnabled(false);
            btnservidor.setEnabled(false);
            ipServer.setEnabled(false);

            (new ClientConnectToServer(TheIP)).start();

            SetText("\nComenzamos Cliente!");
            AppenText("\nNos intentamos conectar al servidor: "+TheIP);
        }
        bSalir.setEnabled(true);
        bEnviar.setEnabled(true);
        etTexto.setEnabled(true);
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    new EnvioMensajesCliente().start();

            }
        });
    }

    public void AppenText(String text)
    {
        runOnUiThread(new appendUITextView(text+"\n"));
    }

    public void SetText(String text)
    {
        runOnUiThread(new setUITextView(text));
    }

    public class WaitingClientThread extends Thread
    {
        public void run()
        {
            SetText("Esperando Usuario...");
            try
            {
                //Abrimos el socket
                serverSocket = new ServerSocket(mPuerto);

                //Mostramos un mensaje para indicar que estamos esperando en la direccion ip y el puerto...
                AppenText("Creado el servidor\n Dirección: "+getIpAddress()+" Puerto: "+serverSocket.getLocalPort());

                //Creamos un socket que esta a la espera de una conexion de cliente
                socket = serverSocket.accept();

                //Una vez hay conexion con un cliente, creamos los streams de salida/entrada
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                }catch(Exception e){ e.printStackTrace();}

                ConectionEstablished=true;

                //Iniciamos el hilo para la escucha y procesado de mensajes
                (HiloEscucha=new GetMessagesThread()).start();

                //Enviamos mensajes desde el servidor.

                HiloEspera=null;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public class ClientConnectToServer extends Thread
    {
        String mIp;
        ClientConnectToServer(String ip){mIp=ip;}
        public void run()
        {
            // Connect to server
            try {
                SetText("Conectando con el servidor: " + mIp + ":" + mPuerto + "...\n\n");//Mostramos por la interfaz que nos hemos conectado al servidor} catch (IOException e) {

                socket = new Socket(mIp, mPuerto);//Creamos el socket

                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                }catch(Exception e){ e.printStackTrace();}

                ConectionEstablished=true;
                //Iniciamos el hilo para la escucha y procesado de mensajes
                (HiloEscucha=new GetMessagesThread()).start();



            } catch (Exception e) {
                e.printStackTrace();
                AppenText("Error: " + e.getMessage());
            }
        }

    }

    private class EnvioMensajesServidor extends Thread
    {

        public void run()
        {

            //String messages= "Bienvenido usuario a mi chat ¿Estás bien? Bueno, pues molt bé, pues adiós";
         //  final int sleeptime=1000;

                    String messages = etTexto.getText().toString();
                   sendMessage(messages);
                    etTexto.setText("");



           // DisconnectSockets();
        }
    }

    public class EnvioMensajesCliente extends Thread
    {
        public void run()
        {
            //String messages="Hola servidor No mucho, pero no te voy a contar mi vida Pues adiós :(";
           // final int sleeptime=1000;
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //sendVariousMessages(messages,sleeptime);


           // DisconnectSockets();
        }
    }

    public void DisconnectSockets()
    {
        if(ConectionEstablished)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    btncliente.setEnabled(true);
                    btnservidor.setEnabled(true);
                    ipServer.setEnabled(true);
                }
            });
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
    }

    public void sendVariousMessages(String msgs, int time)
    {
        if(msgs!=null  /*&&time!=null && msgs.length==time.length*/)
            for(int i=0; i<1; i++)
            {
                sendMessage(msgs);
                try {
                   Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
    }

    private void sendMessage(String txt)
    {
        new SendMessageSocketThread(txt).start();
    }

    public class SendMessageSocketThread extends Thread
    {
        private String msg;

        SendMessageSocketThread(String message)
        {
            msg=message;
        }

        @Override
        public void run()
        {
            try
            {
                dataOutputStream.writeUTF(msg);//Enviamos el mensaje
                //dataOutputStream.close();
                AppenText("Enviado: "+msg);
            }catch (IOException e)
            {
                e.printStackTrace();
                //message += "¡Algo fue mal! " + e.toString() + "\n";
            }
        }
    }

    //Aqui obtenemos la IP de nuestro terminal
    private String getIpAddress()
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

    public class GetMessagesThread extends Thread
    {
        private boolean executing;
        private String line;


        public void run()
        {
            executing=true;

            while(executing)
            {
                line="";
                line=ObtenerCadena();//Obtenemos la cadena del buffer
                if(!line.equals("") && line.length()!=0)//Comprobamos que esa cadena tenga contenido
                {
                    AppenText("Recibido: "+line);//Procesamos la cadena recibida
                }
            }
        }

        private void setExecuting(){executing= false;}


        private String ObtenerCadena()
        {
            String cadena="";

            try {
                cadena=dataInputStream.readUTF();//Leemos del datainputStream una cadena UTF
                Log.d("ObtenerCadena", "Cadena reibida: "+cadena);

            }catch(Exception e)
            {
                e.printStackTrace();
                executing=false;
            }
            return cadena;
        }
    }

    public class setUITextView implements Runnable
    {
        private String text;
        private setUITextView(String text){this.text=text;}
        public void run(){myTV.setText(text);}
    }

    public class appendUITextView implements Runnable
    {
        private String text;
        private appendUITextView(String text){this.text=text;}
        public void run(){myTV.append(text);}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DisconnectSockets();
    }

}

