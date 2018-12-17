package es.fempa.juanpomares.sockets;

import java.io.IOException;

public class SendMessageSocketThread extends Thread{

    pantallaTexto m;
    private String msg;

    SendMessageSocketThread(String message, pantallaTexto _m)
    {
        this.m = _m;
        msg=message;
    }

    @Override
    public void run()
    {
        try
        {
            m.dataOutputStream.writeUTF(msg);//Enviamos el mensaje
            //dataOutputStream.close();
            m.AppenText("Enviado: "+msg);
        }catch (IOException e)
        {
            e.printStackTrace();
            //message += "¡Algo fue mal! " + e.toString() + "\n";
        }
    }


}
