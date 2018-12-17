package es.fempa.juanpomares.sockets;

public class EnvioMensajesCliente extends Thread {

    pantallaTexto m;

    public EnvioMensajesCliente(pantallaTexto _m){
        this.m = _m;
    }
    public void run()
    {
        //String messages="Hola servidor No mucho, pero no te voy a contar mi vida Pues adiós :(";
        // final int sleeptime=1000;
        try {
            Thread.sleep(1000);
            String messages = m.etTexto.getText().toString();
            m.sendMessage(messages);
            m.vaciarChat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //sendVariousMessages(messages,sleeptime);


        // DisconnectSockets();
    }
}
