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

    public Display(Layout layout) {
        super(layout);
        createTempDisplay();
    }

    public void createTempDisplay() {
        // just display text for the registers until logic can update calc
        Label tRegister = new Label("T: ");
        Label zRegister = new Label("Z: ");
        Label yRegister = new Label("Y: ");
        Label xRegister = new Label("X: ");

        tRegisterRow.add(tRegister);
        zRegisterRow.add(zRegister);
        yRegisterRow.add(yRegister);
        xRegisterRow.add(xRegister);

        // add RegisterRows to the display container
        this.addAll(tRegisterRow, zRegisterRow, yRegisterRow, xRegisterRow);
    }

    public void updateDisplay() {
        // functions copied from the lecture video
        // setRow(tRegisterRow, "T:", calc.getT());
        // setRow(zRegisterRow, "Z:", calc.getZ());
        // setRow(yRegisterRow, "Y:", calc.getY());
        // setRow(xRegisterRow, "X:", calc.getX());
    }
}
