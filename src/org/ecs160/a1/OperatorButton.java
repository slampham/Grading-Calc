package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.RoundBorder;

public class OperatorButton extends Button {
    public OperatorButton(String text) {
        super(text);

        this.getUnselectedStyle().setBorder(
                RoundBorder.create().rectangle(true).color(ColorUtil.GRAY)
        );

        this.getUnselectedStyle().setBgTransparency(255);
        this.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
        this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // Can add selectedStyle for when button is pressed. Otherwise it defaults to white BG with light blue text
        this.getAllStyles().setMargin(10, 10, 10, 10); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT
    }
}
