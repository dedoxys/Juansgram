package es.fempa.juanpomares.sockets;

public class EnvioMensajesCliente extends Thread {

    pantallaText m;

    public EnvioMensajesCliente(pantallaText _m){
        this.m = _m;
    }
    public void run()
    {
        //String messages="Hola servidor No mucho, pero no te voy a contar mi vida Pues adiós :(";
        // final int sleeptime=1000;
        try {
            Thread.sleep(1000);
            String messages = m.nombre + ": " + m.etTexto.getText().toString();
            m.sendMessage(messages);
            m.vaciarChat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //sendVariousMessages(messages,sleeptime);


        // DisconnectSockets();
    }
}
