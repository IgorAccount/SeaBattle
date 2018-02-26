package ru.igor.tests.game;

import ru.igor.tests.view.AIField;
import ru.igor.tests.view.PlayerField;
import ru.igor.tests.view.SBFrame;

import javax.swing.*;

import java.awt.*;

import static ru.igor.tests.constants.Constants.SHIPCELLS;

public class GameLogic {

    private boolean running = false;
    private PlayerField playerField;
    private AIField aiPlayer;
    String messageDialog;
    SBFrame sbFrame;

    public GameLogic(PlayerField playerField, AIField aiPlayer, SBFrame sbFrame) {
        this.playerField = playerField;
        this.aiPlayer = aiPlayer;
        this.sbFrame = sbFrame;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

        private void canWinAi() {
        int localCounter = 0;
        int[][] playerFildNew = playerField.getPlayerField();
        for (int i = 0; i < playerFildNew.length; i++) {
            for (int j = 0; j < playerFildNew[i].length; j++) {
                if (playerFildNew[i][j] == SHIPCELLS){
                    localCounter++;
                }
            }
        }
        if(localCounter == 0){
           messageDialog = "Компьютер выиграл";
           callDialogFrame();
           stop();
        }
    }

    private void canWinPlayer() {
        int[][] aiFieldNew = aiPlayer.getaIField();
        int localCounter = 0;
        for (int i = 0; i < aiFieldNew.length; i++) {
            for (int j = 0; j < aiFieldNew[i].length; j++) {
                if (aiFieldNew[i][j] == SHIPCELLS){
                    localCounter++;

                }
            }
        }
        if(localCounter == 0){
            messageDialog = "Вы выиграли";
            callDialogFrame();
            stop();
        }
    }

    private void callDialogFrame(){
        int a = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),"Хотите ли еще сыграть?", messageDialog, JOptionPane.YES_NO_OPTION );
        if(a == 0){
            //SBFrame sbFrame = new SBFrame();
            sbFrame.removePanels();

        }
        if(a == 1){
            System.exit(0);
        }    }

    public void mainLoop() {
        while (running) {
            if(playerField.canAiMakeNextStep){
                playerField.attackPlayerField();
                playerField.draw();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                canWinAi();
                if(playerField.canAiMakeNextStep == false) {
                    System.out.println("MY STEP");
                    aiPlayer.canPlayerMakeNextStep = true;
                }
            } else if(aiPlayer.canPlayerMakeNextStep){
                aiPlayer.draw();
                canWinPlayer();
                if(aiPlayer.canPlayerMakeNextStep == false) {playerField.canAiMakeNextStep = true;
                    System.out.println("PLAYER STEP");
                }
            }
        }
    }
}
