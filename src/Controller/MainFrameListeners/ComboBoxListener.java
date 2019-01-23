package Controller.MainFrameListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBoxListener implements ActionListener {

    private JComboBox optionsMenu;

    public ComboBoxListener(JComboBox optionsMenu){
        this.optionsMenu = optionsMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String selectedItem  = (String)optionsMenu.getSelectedItem();
        switch (selectedItem){
            case("Load"):
                break;
            case("Save"):
                break;
            case("Quit"):
                break;

        }


    }
}
