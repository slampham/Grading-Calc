package org.ecs160.a1;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;

public class Display extends Container {
    // four containers to populate display
    Container tRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
    Container zRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
    Container yRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
    Container xRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));

    Calc calc = new Calc();

    public Display(Layout layout) {
        super(layout);
        updateDisplay();
    }

    public void updateDisplay() {
        // just display text for the registers until logic can update calc
        double[] registers = calc.getRegisters();

        Label tRegister = new Label("T: " + registers[3]);
        Label zRegister = new Label("Z: " + registers[2]);
        Label yRegister = new Label("Y: " + registers[1]);
        Label xRegister = new Label("X: " + registers[0]);

        tRegisterRow.add(tRegister);
        zRegisterRow.add(zRegister);
        yRegisterRow.add(yRegister);
        xRegisterRow.add(xRegister);

        // add RegisterRows to the display container
        this.addAll(tRegisterRow, zRegisterRow, yRegisterRow, xRegisterRow);
    }
}
