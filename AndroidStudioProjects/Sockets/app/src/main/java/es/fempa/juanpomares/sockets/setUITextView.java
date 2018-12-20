package es.fempa.juanpomares.sockets;

import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.support.v4.widget.TextViewCompat.setTextAppearance;

public class setUITextView implements Runnable{

    private String text;
    pantallaText m;

    public setUITextView(String text, pantallaText _m) {
        this.text=text;
        this.m = _m;
    }

    public void run(){
        m.myTV = new Bocadillo(new ContextThemeWrapper(m,R.style.BocadilloDerecha),null);
        LinearLayout.LayoutParams bocataDerecha = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bocataDerecha.setMargins(10, 10, 10, 10);
        bocataDerecha.gravity = Gravity.END;
        m.myTV.setText(text);
        m.ltexto.addView(m.myTV, bocataDerecha);
    }
}
