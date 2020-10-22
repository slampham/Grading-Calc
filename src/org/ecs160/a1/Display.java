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

    Label tRegister = new Label("T: ");
    Label zRegister = new Label("Z: ");
    Label yRegister = new Label("Y: ");
    Label xRegister = new Label("X: ");

    Calc calc = new Calc();

    public Display(Layout layout) {
        super(layout);

        tRegisterRow.add(tRegister);
        zRegisterRow.add(zRegister);
        yRegisterRow.add(yRegister);
        xRegisterRow.add(xRegister);

        // Add registers to Display Container
        this.addAll(tRegisterRow, zRegisterRow, yRegisterRow, xRegisterRow);
    }

    public void updateDisplay() {
        /* TODO: NEED HELP WITH UPDATING DISPLAY WHEN A KEY IS PRESSED. FOR EXAMPLE:
        * if '2' is pressed, class Keypad should call updateDisplay(). However, I'm having
        * trouble understanding how to do that. Perhaps singleton pattern? Or static class?
        * */

        // Get registers after doing button press, and update display
        double[] registers = calc.getRegisters();

        tRegister.setText("T: " + registers[3]);
        zRegister.setText("Z: " + registers[2]);
        yRegister.setText("Y: " + registers[1]);
        xRegister.setText("X: " + registers[0]);
    }
}
