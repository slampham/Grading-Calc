package org.ecs160.a1;


import static com.codename1.ui.CN.*;

import com.codename1.ui.*;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;

import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose
 * of building native mobile applications using Java.
 */
public class AppMain {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new FormRPNCalc();
        hi.show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}

/*
class TitledForm extends Form {
    public TitledForm() {
        Command cOk = new Command("Ok");
        Command cCancel = new Command("Cancel");
        Command[] cmds = new Command[]{cOk, cCancel};
        TextField myTF = new TextField();
        Command c = Dialog.show("Enter the title:", myTF, cmds);

        if (c == cOk)
            setTitle(myTF.getText());
        else
            setTitle("Title not specified");
        show();
    }
}
*/
