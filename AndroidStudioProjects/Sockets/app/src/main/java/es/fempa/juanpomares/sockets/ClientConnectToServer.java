package es.fempa.juanpomares.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientConnectToServer extends Thread {

    String mIp;
    pantallaText m;

    public ClientConnectToServer(String ip, pantallaText _m){
        this.m = _m;
        mIp=ip;
    }

    public void run()
    {
        // Connect to server
        try {
            m.SetText("Conectando con el servidor: " + mIp + ":" + m.mPuerto + "...\n\n");//Mostramos por la interfaz que nos hemos conectado al servidor} catch (IOException e) {

            m.socket = new Socket(mIp, m.mPuerto);//Creamos el socket
            m.socket.setReuseAddress(true);
            try {
                m.dataInputStream = new DataInputStream(m.socket.getInputStream());
                m.dataOutputStream = new DataOutputStream(m.socket.getOutputStream());
            }catch(Exception e){ e.printStackTrace();}

            m.ConectionEstablished=true;
            //Iniciamos el hilo para la escucha y procesado de mensaje
            (m.HiloEscucha=new GetMessagesThread(m)).start();

            m.sendMessage("###nombre##:"+m.nombre);



        } catch (Exception e) {
            e.printStackTrace();
            m.AppenText("Error: " + e.getMessage());
        }
    }
}
