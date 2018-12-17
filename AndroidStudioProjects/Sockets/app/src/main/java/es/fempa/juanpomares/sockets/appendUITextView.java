package es.fempa.juanpomares.sockets;

public class appendUITextView implements Runnable{

    private String text;
    MainActivity m;

    public appendUITextView(String text, MainActivity _m){
        this.text=text;
        this.m = _m;
    }
    public void run(){
        m.myTV.append(text);
    }
}
