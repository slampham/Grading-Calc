package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;

public class ScienceButton extends Button {
    public ScienceButton(String text) {
        super(text);

        this.getUnselectedStyle().setBgTransparency(255);
        this.getUnselectedStyle().setBgColor(ColorUtil.GREEN);
        this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // Can add selectedStyle for when button is pressed. Otherwise it defaults to white BG with light blue text
        this.getAllStyles().setMargin(25, 25, 15, 15); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT
    }
}
