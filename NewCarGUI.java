import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCarGUI extends JFrame{
    private JTextField txtLicNum;
    private JTextField txtModel;
    private JTextField txtBrand;
    private JRadioButton a5SpeedManualRadioButton;
    private JRadioButton a6SpeedManualRadioButton;
    private JRadioButton petrolRadioButton;
    private JRadioButton dieselRadioButton;
    private JTextField txtVmax;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel mainPanel;

    public NewCarGUI(JComboBox comboBox) {
        setContentPane(mainPanel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtLicNum.getText().equals("") && !txtModel.getText().equals("") && !txtBrand.getText().equals("") &&
                        !txtVmax.getText().equals("") && (a5SpeedManualRadioButton.isSelected() || a6SpeedManualRadioButton.isSelected())
                        && (petrolRadioButton.isSelected() || dieselRadioButton.isSelected())) {
                    GearBox gearBox = new GearBox("6-speed", 130, 699, 1, 6);
                    Engine engine = new Engine("Diesel", 200, 1500, 8500);
                    Car car = new Car(false, txtLicNum.getText(), txtBrand.getText() + " " + txtModel.getText(),
                            Integer.parseInt(txtVmax.getText()), engine, gearBox);
                    if (a5SpeedManualRadioButton.isSelected()) {
                        car.gBox.setName("5-speed");
                        car.gBox.setMaxGear(5);
                    } else {
                        car.gBox.setName("6-speed");
                        car.gBox.setMaxGear(6);
                    }
                    if (petrolRadioButton.isSelected()) {
                        car.engine.setName("Petrol");
                    } else {
                        car.engine.setName("Diesel");
                    }
                    car.setvMax(Integer.parseInt(txtVmax.getText()));
                    comboBox.addItem(car);
                    comboBox.setRenderer(new MyObjectListCellRenderer());
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
