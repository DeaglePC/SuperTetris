/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supertetris;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author eagle
 */
public class SuperTetris {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tetris a = new Tetris();
        JFrame fm = new JFrame("俄罗斯方块-dpc");

        fm.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                switch (arg0.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case 83:
                    case 115:
                        a.keyDown();
                        break;
                    case KeyEvent.VK_LEFT:
                    case 65:
                    case 97:
                        a.keyLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                    case 68:
                    case 100:
                        a.keyRight();
                        break;
                    case KeyEvent.VK_UP:
                    case 87:
                    case 119:
                        a.keyUp();
                        break;
                    case KeyEvent.VK_SPACE:
                        a.keySpace();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
            }
        });
        fm.add(a);
        fm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fm.setSize(351, 440);

        // 让窗口在屏幕中间出现
        int screenWidth, screenHeight;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        fm.setLocation(screenWidth / 2 - 175, screenHeight / 2 - 220);

        fm.setResizable(false);
        fm.setVisible(true);
    }
}
