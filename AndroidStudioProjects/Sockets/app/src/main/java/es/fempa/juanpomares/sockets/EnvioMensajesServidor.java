package es.fempa.juanpomares.sockets;

public class EnvioMensajesServidor extends Thread {

    pantallaText m;

    public EnvioMensajesServidor(pantallaText _m){
        this.m = _m;
    }

    public void run()
    {
        //String messages= "Bienvenido usuario a mi chat ¿Estás bien? Bueno, pues molt bé, pues adiós";
        //  final int sleeptime=1000;
        String messages = m.nombre + ": " + m.etTexto.getText().toString();
        m.sendMessage(messages);
        m.vaciarChat();
        // DisconnectSockets();
    }
}
