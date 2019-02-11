package Controller.MainFrameListeners;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerTest implements KeyListener {

    //TODO Link somehow with absorbers?
    //TODO Link with connections

    public KeyListenerTest(String id){
        System.out.println("Init KeyListener : " + id + " Mode.");
        //a.getInputMap.put(KeyStroke.getKeyStroke("A"), System.out.println("Test123"));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key : " + e.getKeyChar() + " pressed.");

        //Dummy code.
        if (e.getKeyChar() == 'c'){
            System.out.println("Absorber fire!");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    //TODO Link connections (flippers and what not)

}
