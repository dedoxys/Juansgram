package es.fempa.juanpomares.sockets;

import android.content.Intent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientConnectToServer extends Thread {

    String mIp;
    MainActivity m;

    public ClientConnectToServer(String ip, MainActivity _m){
        this.m = _m;
        mIp=ip;
    }

    public void run()
    {
        // Connect to server
        try {
            m.SetText("Conectando con el servidor: " + mIp + ":" + m.mPuerto + "...\n\n");//Mostramos por la interfaz que nos hemos conectado al servidor} catch (IOException e) {

            m.socket = new Socket(mIp, m.mPuerto);//Creamos el socket

            try {
                m.dataInputStream = new DataInputStream(m.socket.getInputStream());
                m.dataOutputStream = new DataOutputStream(m.socket.getOutputStream());

                Intent intent =  new Intent(m, pantallaTexto.class);
                intent.putExtra("boolean", false);
                m.startActivity(intent);

            }catch(Exception e){ e.printStackTrace();}

            m.ConectionEstablished=true;
            //Iniciamos el hilo para la escucha y procesado de mensajes
            (m.HiloEscucha=new GetMessagesThread(m)).start();



        } catch (Exception e) {
            e.printStackTrace();
            //m.AppenText("Error: " + e.getMessage());
        }
    }
}
