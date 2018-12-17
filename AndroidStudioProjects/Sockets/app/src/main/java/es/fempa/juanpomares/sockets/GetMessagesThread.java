package es.fempa.juanpomares.sockets;

import android.util.Log;

public class GetMessagesThread extends Thread {

    private boolean executing;
    private String line;
    MainActivity m;

    public GetMessagesThread(MainActivity _m){
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
                m.AppenText("Recibido: "+line);//Procesamos la cadena recibida
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
