package es.fempa.juanpomares.sockets;

import android.telecom.DisconnectCause;
import android.util.Log;
import android.view.View;

public class GetMessagesThread extends Thread {

    private boolean executing;
    private String line;
    pantallaText m;

    public GetMessagesThread(pantallaText _m){
        this.m = _m;
    }


    public void run()
    {
        executing=true;

        while(executing)
        {
            line="";
            line=ObtenerCadena();//Obtenemos la cadena del buffer
            if(!line.equals("") && line.length()!=0)//Comprobamos que esa cadena tenga contenido
            {
                if(line.contains("###nombre##:")){
                    String[] temp = line.split(":");
                    m.setCabecera(temp[1]);
                }else if(line.equals("##disconeto###:")){
                    m.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            m.DisconnectSockets();
                            m.finish();
                        }
                    });
                }else{
                    m.runOnUiThread(new appendUITextView(line, m));//Procesamos la cadena recibida
                }

            }
        }
    }

    public void setExecuting(){executing= false;}


    private String ObtenerCadena()
    {
        String cadena="";

        try {
            cadena = m.dataInputStream.readUTF();//Leemos del datainputStream una cadena UTF
            Log.d("ObtenerCadena", "Cadena reibida: "+cadena);

        }catch(Exception e)
        {
            e.printStackTrace();
            executing=false;
        }
        return cadena;
    }
}
