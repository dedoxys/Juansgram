package es.fempa.juanpomares.sockets;

public class setUITextView implements Runnable{

    private String text;
    MainActivity m;
    pantallaTexto p;

    public setUITextView(String text, MainActivity _m) {
        this.text=text;
        this.m = _m;
    }

    public setUITextView(String text, pantallaTexto _p) {
        this.text=text;
        this.p = _p;
    }


    public void run(){
        if(m != null)
            m.myTV.setText(text);
        else
            p.myTV.setText(text);
    }
}
