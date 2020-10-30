package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

public class RPNForm extends com.codename1.ui.Form {
    Calc calc = new Calc();
    Display display = new Display(new BoxLayout(BoxLayout.Y_AXIS), calc);
    Keypad keypad = new Keypad(new GridLayout(6,1), calc, display);

    public RPNForm() {
        setLayout(new BorderLayout());
        getToolbar().hideToolbar();

        // adds safe areas for iPhones (raises keypad)
        display.setSafeArea(true);
        keypad.setSafeArea(true);
        display.getAllStyles().setPadding(20, 20, 0, 0);
        keypad.getAllStyles().setPadding(180, 0, 0, 0);

        add(BorderLayout.NORTH, display);
        add(BorderLayout.CENTER, keypad);
    }
}
