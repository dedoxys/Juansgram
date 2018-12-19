package es.fempa.juanpomares.sockets;

public class appendUITextView implements Runnable{

    private String text;
    pantallaText m;

    public appendUITextView(String text, pantallaText _m){
        this.text=text;
        this.m = _m;
    }
    public void run(){
        m.myTV.append(text);
    }
}
