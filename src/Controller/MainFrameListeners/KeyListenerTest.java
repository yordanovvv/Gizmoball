package Controller.MainFrameListeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerTest implements KeyListener {

    public KeyListenerTest(){
        System.out.println("Key Listener initialised");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key : " + e.getKeyChar() + " pressed.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
