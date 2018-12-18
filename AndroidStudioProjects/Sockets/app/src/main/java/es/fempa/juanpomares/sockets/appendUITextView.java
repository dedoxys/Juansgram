package es.fempa.juanpomares.sockets;

public class appendUITextView implements Runnable{

    private String text;
    MainActivity m;
    pantallaTexto p;

    public appendUITextView(String text, MainActivity _m){
        this.text=text;
        this.m = _m;
    }

    public appendUITextView(String text, pantallaTexto _p) {
        this.text=text;
        this.p = _p;
    }

    public void run(){

        if(m != null)
            m.myTV.append(text);
        else
            p.myTV.append(text);
    }
}
