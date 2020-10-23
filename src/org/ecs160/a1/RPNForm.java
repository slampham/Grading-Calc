package org.ecs160.a1;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

public class RPNForm extends com.codename1.ui.Form {
    Calc calc = new Calc();
    Display display = new Display(new BoxLayout(BoxLayout.Y_AXIS), calc);
    Keypad keypad = new Keypad(new GridLayout(5,1), calc, display);

    public RPNForm() {
        setLayout(new BorderLayout());
        setTitle("RPN Calculator v0.1");

        add(BorderLayout.NORTH, display);
        add(BorderLayout.CENTER, keypad);
    }
}
