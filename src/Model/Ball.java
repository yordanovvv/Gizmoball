package Model;


import java.util.Observable;
import java.util.Vector;

public class Ball extends Observable {

    int ID;
    double radius;
    //Vect velocity;

    private int getID(){
        return  ID;
    }

    private boolean getState(){
        return false;
    }


}
