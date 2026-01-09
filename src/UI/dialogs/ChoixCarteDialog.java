package ui.dialogs;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChoixCarteDialog extends JDialog {
    int indexChoisi = -1;
    public ChoixCarteDialog(
            Window parent,
            String titre,
            ArrayList<String> options) {

        super(parent,
              titre,
              ModalityType.APPLICATION_MODAL);

        this.setSize(400, 200);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BoxLayout(
            this.getContentPane(),
            BoxLayout.Y_AXIS));

        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            JButton btn = new JButton(option);
            final int idx = i;
            btn.addActionListener(e -> {
                indexChoisi = idx;
                dispose();
            });
            this.add(btn);
        }
    }
    public int afficher() {
        this.setVisible(true);
        return indexChoisi;
    }
    
    
}
