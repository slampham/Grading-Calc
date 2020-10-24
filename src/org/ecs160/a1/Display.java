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

    Calc calc;

    public Display(Layout layout, Calc calc) {
        super(layout);
        this.calc = calc;

        tRegisterRow.add(tRegister);
        zRegisterRow.add(zRegister);
        yRegisterRow.add(yRegister);
        xRegisterRow.add(xRegister);

        this.addAll(tRegisterRow, zRegisterRow, yRegisterRow, xRegisterRow); // Add registers to Display Container
        this.update();
    }

    public void update() {
        /* Get registers after doing button press, and update display */
        double[] registers = calc.getRegisters();

        tRegister.setText("T: " + registers[3]);
        zRegister.setText("Z: " + registers[2]);
        yRegister.setText("Y: " + registers[1]);
        xRegister.setText("X: " + registers[0]);

        this.revalidate();
    }
    
    public void viewLists() {
        // TODO: IMPLEMENT PERSISTENT STORAGE. 92, 62, 83, 92, 56, 100, 30
        tRegister.setText("RCL 0:   4    5   18   21   63    0   28   76   16 ");
        zRegister.setText("RCL 1:   41   40   39    2   81   11   46   53   42   77");
        yRegister.setText("RCL 2:   37   12   96   32   99   87   72   30   27");
        xRegister.setText("RCL 3:   73   60    8   79   52   23  100   24 ");

        this.revalidate();
    }

    public void summary() {
        tRegister.setText("Grades: 8 Z's | 5 F's | 20 C's | 32 B's | 20 A's");
        zRegister.setText("Standard Deviation: 17");
        yRegister.setText("Min: 0 | Max: 97");
        xRegister.setText("Mean: 77 | Median: 81 | Mode: 0");

        this.revalidate();
    }
}
