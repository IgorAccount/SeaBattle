package ru.igor.tests.view;

import javax.swing.*;
import java.awt.*;

public class AIInformation extends JPanel {

    public AIInformation(){
        setLayout(new BorderLayout());
        setSize(300,300);
        JLabel jLabel = new JLabel("Поле противника");
        jLabel.setFont(new Font("TimesNewRoman", 20,20));
        add(jLabel, BorderLayout.NORTH);
    }
}
