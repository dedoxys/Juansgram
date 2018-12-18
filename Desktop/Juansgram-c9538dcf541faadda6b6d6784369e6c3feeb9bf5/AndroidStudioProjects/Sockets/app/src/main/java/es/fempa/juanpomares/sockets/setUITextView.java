package es.fempa.juanpomares.sockets;

public class setUITextView implements Runnable{

    private String text;
    pantallaText m;

    public setUITextView(String text, pantallaText _m) {
        this.text=text;
        this.m = _m;
    }

    public void run(){
        m.myTV.setText(text);
    }
}
