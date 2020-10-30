package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Border;

import java.util.*;
import java.util.List;

public class Display extends Container {
    Label tRegister = new Label("T: ");
    Label zRegister = new Label("Z: ");
    Label yRegister = new Label("Y: ");
    Label xRegister = new Label("X: ");

    Calc calc;

    public Display(Layout layout, Calc calc) {
        super(layout);
        this.calc = calc;

        this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.GREEN)); // Set border around 5th row

        // four containers to populate display
        Container tRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container zRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container yRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container xRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));

        tRegisterRow.add(tRegister);
        zRegisterRow.add(zRegister);
        yRegisterRow.add(yRegister);
        xRegisterRow.add(xRegister);

        this.addAll(tRegisterRow, zRegisterRow, yRegisterRow, xRegisterRow); // Add registers to Display Container
        this.updateRegisters();
        this.revalidate();
    }

    public void updateRegisters() {
        /* Get registers after doing button press, and update display */
        double[] registers = calc.getRegisters();

        tRegister.setText("T: " + registers[2]);
        zRegister.setText("Z: " + registers[1]);
        yRegister.setText("Y: " + registers[0]);
        xRegister.setText("X: " + calc.X);
    }
    
    public void viewLists() {
        tRegister.setText("RCL 0: " + calc.lists().get(0).toString());
        zRegister.setText("RCL 1: " + calc.lists().get(1).toString());
        yRegister.setText("RCL 2: " + calc.lists().get(2).toString());
        xRegister.setText("RCL 3: " + calc.lists().get(3).toString());
    }

    public void viewSummary() {
        tRegister.setText("Grades: 8 Z's | 5 F's | 20 C's | 32 B's | 20 A's");
        tRegister.setText(calc.gradeHistogram().toString());
        zRegister.setText("STD Dev: 17");
        yRegister.setText("Min: 0 | Max: 97");
        xRegister.setText("Mean: 77 | Median: 81 | Mode: 0");
    }
}
