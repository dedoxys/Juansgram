package es.fempa.juanpomares.sockets;

public class setUITextView implements Runnable{

    private String text;
    MainActivity m;

    public setUITextView(String text, MainActivity _m) {
        this.text=text;
        this.m = _m;
    }

    public void run(){
        m.myTV.setText(text);
    }
}
