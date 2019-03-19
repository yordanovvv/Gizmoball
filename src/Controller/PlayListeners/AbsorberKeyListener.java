package Controller.PlayListeners;

import Model.Ball;
import Model.iGizmo;
import Model.iModel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

//TODO : Refactor this to absorberListener or something?
public class AbsorberKeyListener implements KeyListener {

    //TODO Link with connections

    iModel model;
    char key;
    iGizmo giz; //not used

    public AbsorberKeyListener(String id, iModel model, char key, iGizmo giz){
        System.out.println("Init KeyListener for gizmo id : " + id);

        this.model = model;
        this.key = key;
        this.giz = giz;

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == key ){
          //  System.out.println("Absorber fire!");
            //model.getBall().setStopped(false);
            Ball b = model.getAbsorber().activateAbsorber();
            try {
                model.getAudio("res/clips/laser_cannon.wav");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (UnsupportedAudioFileException e1) {
                e1.printStackTrace();
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            }
            if (b != null){
            //Trigger the absorber!
          //  System.out.println(b);
            //Vect velo = b.getVelo();
            //model.getBall().setVelo(velo);
            //model.getBall().setStopped(false);

           }
            //model.setBall() = b;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    //TODO Link connections (flippers and what not)

}
