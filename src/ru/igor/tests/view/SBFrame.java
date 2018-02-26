package ru.igor.tests.view;

import ru.igor.tests.game.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SBFrame extends JFrame {
    private PlayerField playerField;
    private AIField aiField;
    private JMenuBar jMenuBar;
    private JMenu jMenu;
    private JMenuItem jMenuItem;
    private JMenuItem jMenuItem2;
    JPanel jPanel;

    public SBFrame() {
        setSize(new Dimension(700, 700));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //firsLoading();
        init();
        setVisible(true);
    }

    public void init() {
        playerField = new PlayerField();
        aiField = new AIField();

        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2 , 2));
        jPanel.add(playerField);
        jPanel.add(aiField);
        jPanel.add(new MyInformation());
        jPanel.add(new AIInformation());
        add(jPanel);
        GameLogic gameLogic = new GameLogic(playerField, aiField, this);
        gameLogic.start();
        setVisible(true);
        gameLogic.mainLoop();
        setVisible(true);
    }

    public void removePanels(){
        jPanel.removeAll();
        jPanel.repaint();
        playerField = new PlayerField();
        aiField = new AIField();
        jPanel.setLayout(new GridLayout(2 , 2));
        jPanel.add(playerField);
        jPanel.add(aiField);
        jPanel.add(new MyInformation());
        jPanel.add(new AIInformation());
        add(jPanel);
        GameLogic gameLogic = new GameLogic(playerField, aiField, this);
        gameLogic.start();
        setVisible(true);
        gameLogic.mainLoop();
        setVisible(true);

    }

    private void firsLoading() {
        jMenuBar = new JMenuBar();
        jMenuBar.setLayout(new BorderLayout());
        jMenu = new JMenu("Меню");
        jMenuBar.add(jMenu);
        jMenuItem = new JMenuItem("Новая игра");
        jMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                setVisible(true);
            }
        });
        jMenuItem2 = new JMenuItem("Выход");
        jMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jMenu.add(jMenuItem);
        jMenu.add(jMenuItem2);
        add(jMenuBar, BorderLayout.NORTH);
    }
}
