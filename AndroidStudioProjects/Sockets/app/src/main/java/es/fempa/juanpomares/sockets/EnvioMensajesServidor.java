package es.fempa.juanpomares.sockets;

public class EnvioMensajesServidor extends Thread {

    pantallaTexto m;

    public EnvioMensajesServidor(pantallaTexto _m){
        this.m = _m;
    }

    public void run()
    {
        //String messages= "Bienvenido usuario a mi chat ¿Estás bien? Bueno, pues molt bé, pues adiós";
        //  final int sleeptime=1000;
        String messages = m.etTexto.getText().toString();
        m.sendMessage(messages);
        m.vaciarChat();
        // DisconnectSockets();
    }
}
