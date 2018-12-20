package es.fempa.juanpomares.sockets;

import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class appendUITextView implements Runnable{

    private String text;
    pantallaText m;

    public appendUITextView(String text, pantallaText _m){
        this.text=text;
        this.m = _m;
    }
    public void run(){

        m.myTV = new Bocadillo(new ContextThemeWrapper(m,R.style.BocadilloIzquierda),null);
        LinearLayout.LayoutParams bocataIzquierda = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bocataIzquierda.setMargins(10, 10, 10, 10);
        bocataIzquierda.gravity = Gravity.START;
        m.myTV.setText(text);
        m.ltexto.addView(m.myTV, bocataIzquierda);
    }
}
