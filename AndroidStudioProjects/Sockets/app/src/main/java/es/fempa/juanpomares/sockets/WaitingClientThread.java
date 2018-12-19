package es.fempa.juanpomares.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class WaitingClientThread extends Thread{

    pantallaText m;

    public WaitingClientThread(pantallaText _m){
        this.m = _m;
    }

    public void run()
    {
        m.SetText("Esperando Usuario...");
        try
        {
            //Abrimos el socket
            m.serverSocket = new ServerSocket(m.mPuerto);

            //Mostramos un mensaje para indicar que estamos esperando en la direccion ip y el puerto...
            m.AppenText("Creado el servidor\n Dirección: "+m.getIpAddress()+" Puerto: "+m.serverSocket.getLocalPort());

            //Creamos un socket que esta a la espera de una conexion de cliente
            m.socket = m.serverSocket.accept();

            //Una vez hay conexion con un cliente, creamos los streams de salida/entrada
            try {
                m.dataInputStream = new DataInputStream(m.socket.getInputStream());
                m.dataOutputStream = new DataOutputStream(m.socket.getOutputStream());
            }catch(Exception e){ e.printStackTrace();}

            m.ConectionEstablished=true;

            //Iniciamos el hilo para la escucha y procesado de mensajes
            (m.HiloEscucha = new GetMessagesThread(m)).start();



            //Enviamos mensajes desde el servidor.

            m.HiloEspera=null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
