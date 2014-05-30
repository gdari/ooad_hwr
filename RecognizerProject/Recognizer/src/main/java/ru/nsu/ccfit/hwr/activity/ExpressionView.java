package ru.nsu.ccfit.hwr.activity;


/**
 * Created by Баира on 23.12.13.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ExpressionView extends WebView{
    public ExpressionView(Context context ) {
        super(context);
        init();
    }

    public ExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpressionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setBuiltInZoomControls(true);
        loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
                + "MathJax.Hub.Config({ "
                + "showMathMenu: false, "
                + "jax: ['input/TeX','output/HTML-CSS'], "
                + "extensions: ['tex2jax.js'], "
                + "TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
                + "'noErrors.js','noUndefined.js'] } " + "});</script>"
                + "<script type='text/javascript' "
                + "src='file:///android_asset/MathJax/MathJax.js'"
                + "></script><span id='math'></span>", "text/html", "utf-8", "");
    }

    private String doubleEscapeTeX(String s) {
        String t="";
        for (int i=0; i < s.length(); i++) {
            if (s.charAt(i) == '\'') t += '\\';
            if (s.charAt(i) != '\n') t += s.charAt(i);
            if (s.charAt(i) == '\\') t += "\\";
        }
        return t;
    }

    public void showString(String str) {
        loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
                +doubleEscapeTeX(str)+"\\\\]';");
        loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
    }

    public void clear() {
        loadUrl("javascript:document.getElementById('math').innerHTML='';");
        loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
    }
}
