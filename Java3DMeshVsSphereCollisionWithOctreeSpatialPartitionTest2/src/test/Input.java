package test;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Input class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Input extends KeyAdapter {
    
    public static boolean[] keyPressed = new boolean[256];

    public static boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
    }
    
}
