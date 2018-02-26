package ru.igor.tests.view;

import javax.swing.*;
import java.awt.*;

public class MyInformation extends JPanel {

    MyInformation(){
        setLayout(new BorderLayout());
        setSize(300,300);
        JLabel jLabel = new JLabel("Ваше поле с кораблями");
        jLabel.setFont(new Font("TimesNewRoman", 20,20));
        add(jLabel, BorderLayout.NORTH);
    }
}
