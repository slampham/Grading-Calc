package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;

public class OtherButton extends Button {
    public OtherButton(String text) {
        super(text);

        this.getUnselectedStyle().setBgTransparency(255);
        this.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
        this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // Can add selectedStyle for when button is pressed. Otherwise it defaults to white BG with light blue text
        this.getAllStyles().setMargin(50, 50, 20, 20); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT
    }
}
