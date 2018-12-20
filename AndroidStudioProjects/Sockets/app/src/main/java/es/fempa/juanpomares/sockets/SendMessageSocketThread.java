package es.fempa.juanpomares.sockets;

import java.io.IOException;

public class SendMessageSocketThread extends Thread{

    pantallaText m;
    private String msg;

    SendMessageSocketThread(String message, pantallaText _m)
    {
        this.m = _m;
        msg=message;
    }

    @Override
    public void run()
    {

            try
            {
                if (m.ConectionEstablished){
                    m.dataOutputStream.writeUTF(msg);//Enviamos el mensaje
                    //dataOutputStream.close();
                    if(!msg.contains("###nombre##:")) {
                        if(msg.equals("##disconeto###:")){
                            m.DisconnectSockets();
                            m.finish();
                        }else
                            m.runOnUiThread(new setUITextView( msg,m));
                    }
                }

            } catch (IOException e)
        {
            e.printStackTrace();
            //message += "¡Algo fue mal! " + e.toString() + "\n";
        }
    }


}
