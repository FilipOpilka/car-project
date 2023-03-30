import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.SocketOption;
import java.text.DecimalFormat;

public class CarGUI extends Thread {
    private JPanel mainPanel;
    private JTextField txtCarModel;
    private JTextField txtCarLicenseNum;
    private JTextField txtCarWeight;
    private JTextField txtCarSpeed;
    private JButton turnOnButton;
    private JButton turnOffButton;
    private JComboBox comboBoxGarage;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField txtEngineName;
    private JTextField txtEnginePrice;
    private JTextField txtEngineWeight;
    private JTextField txtEngineRPM;
    private JButton accButton;
    private JButton brakeButton;
    private JTextField txtGBName;
    private JTextField txtGBPrice;
    private JTextField txtGBWeight;
    private JTextField txtGBGear;
    private JButton gearUpButton;
    private JButton gearDownButton;
    private JTextField txtClutchName;
    private JTextField txtClutchPrice;
    private JTextField txtClutchWeight;
    private JTextField txtClutchState;
    private JButton pressButton;
    private JButton releaseButton;
    private JPanel map;
    private JLabel icon;

    private Car car;

    DecimalFormat df = new DecimalFormat("#.###");

    public CarGUI(Car c) {
        car = c;
        refresh();
        start();

        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (car != null && comboBoxGarage.getItemCount() != 0) {
                    car.goTo(e.getX(), e.getY());
                }
            }
        });

        turnOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Pressed Turn On button");
                if (!car.isCurrentState()) {
                    car.turnOn();
                    refresh();
                }
            }
        });

        turnOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Pressed Turn Off button");
                car.turnOff();
                refresh();
            }
        });

        accButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car.engine.increaseRPM();
                refresh();
            }
        });

        brakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car.engine.decreaseRPM();
                refresh();
            }
        });

        gearUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car.gBox.increaseGear();
                car.engine.setCurrentRPM(car.engine.getCurrentRPM() - 500);
                refresh();
            }
        });

        gearDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car.gBox.decreaseGear();
                car.engine.setCurrentRPM(car.engine.getCurrentRPM() + 500);
                refresh();
            }
        });

        pressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car.gBox.clutch.press();
                refresh();
            }
        });

        releaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car.gBox.clutch.release();
                refresh();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new NewCarGUI(comboBoxGarage);
                f.pack();
                f.setVisible(true);
                f.setResizable(false);
                refresh();
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car.destination.setX(car.currentPosition.getX());
                car.destination.setY(car.currentPosition.getY());
                comboBoxGarage.removeItem(car);
                try {
                    car.interrupt();
                } catch (NullPointerException n) {
                    Thread.currentThread().interrupt();
                }
                /*try {
                    car.interrupt();
                } catch (InterruptedException e) {
                    return;  // zakończenie metody run()
                }*/
                refresh();
            }
        });

        comboBoxGarage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car = (Car) comboBoxGarage.getSelectedItem();
            }
        });

        KeyAdapter listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                switch(keyCode) {
                    case KeyEvent.VK_UP -> { if(accButton.isEnabled())
                        car.engine.increaseRPM();
                    }
                    case KeyEvent.VK_DOWN -> { if(brakeButton.isEnabled())
                        car.engine.decreaseRPM();
                    }
                    case KeyEvent.VK_RIGHT -> {
                        if (gearUpButton.isEnabled()) {
                            car.gBox.increaseGear();
                            car.engine.setCurrentRPM(car.engine.getCurrentRPM() - 500);
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        if (gearDownButton.isEnabled()) {
                            car.gBox.decreaseGear();
                            car.engine.setCurrentRPM(car.engine.getCurrentRPM() + 500);
                        }
                    }
                    case KeyEvent.VK_SHIFT -> car.gBox.clutch.press();
                }
            }
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    car.gBox.clutch.release();
                }
            }
        };
        turnOnButton.addKeyListener(listener);
        turnOffButton.addKeyListener(listener);
        accButton.addKeyListener(listener);
        brakeButton.addKeyListener(listener);
        pressButton.addKeyListener(listener);
        releaseButton.addKeyListener(listener);
        gearUpButton.addKeyListener(listener);
        gearDownButton.addKeyListener(listener);
    }

    public void run() {
        while(true) {
            refresh();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void refresh() {
        System.out.println("Current threads: " + Thread.activeCount());
        if (car != null && comboBoxGarage.getItemCount() != 0) {
            icon.setLocation((int) car.currentPosition.getX(), (int) car.currentPosition.getY());
            txtCarModel.setText(car.getModel());
            txtCarLicenseNum.setText(car.getLicenseNumber());
            txtCarWeight.setText(Integer.toString(car.getWeight()));
            //txtCarSpeed.setText(df.format(car.getCurrentV()));
            //txtCarSpeed.setText(Integer.toString((int)car.getCurrentV()));
            txtCarSpeed.setText(Double.toString(car.getCurrentV()));
            txtEngineName.setText(car.engine.getName());
            txtEnginePrice.setText(Double.toString(car.engine.getPrice()));
            txtEngineWeight.setText(Integer.toString(car.engine.getWeight()));
            txtEngineRPM.setText(Integer.toString(car.engine.getCurrentRPM()));
            txtGBName.setText(car.gBox.getName());
            txtGBPrice.setText(Double.toString(car.gBox.getPrice()));
            txtGBWeight.setText(Integer.toString(car.gBox.getWeight()));
            txtGBGear.setText(Integer.toString(car.gBox.getCurrentGear()));
            txtClutchName.setText(car.gBox.clutch.getName());
            txtClutchPrice.setText(Double.toString(car.gBox.clutch.getPrice()));
            txtClutchWeight.setText(Integer.toString(car.gBox.clutch.getWeight()));
            txtClutchState.setText(car.gBox.clutch.isClutchState());
            icon.setVisible(true);
            deleteButton.setEnabled(true);

            /*if (car.destination.getX() >= car.currentPosition.getX()) {
                icon.setIcon(new ImageIcon("/D:/IntelliJProjects/po2021/CarProject/src"));
                icon.setVisible(true);
            }
            else {
                icon.setIcon(new ImageIcon("D:/IntelliJProjects/po2021/CarProject/src"));
                icon.setVisible(true);
            }*/

            if (car.isCurrentState()) {
                turnOffButton.setEnabled(true);
                pressButton.setEnabled(true);
                releaseButton.setEnabled(true);
            }
            else {
                turnOnButton.setEnabled(true);
                turnOffButton.setEnabled(false);
                pressButton.setEnabled(false);
                releaseButton.setEnabled(false);

            }

            brakeButton.setEnabled(car.engine.getCurrentRPM() > 900 && car.isCurrentState());
            accButton.setEnabled(car.engine.getCurrentRPM() < car.engine.getMaxRPM() && car.isCurrentState() &&
                    car.getCurrentV() < car.getvMax());

            if (car.isCurrentState()) {
                if (!car.gBox.clutch.getClutchState()) {
                    gearUpButton.setEnabled(false);
                    gearDownButton.setEnabled(false);
                } else {
                    if (car.engine.getCurrentRPM() >= 900 && car.engine.getCurrentRPM() < 2000) {
                        gearUpButton.setEnabled(false);
                        gearDownButton.setEnabled(car.gBox.getCurrentGear() != 1);
                    } else if (car.engine.getCurrentRPM() >= 2000 && car.engine.getCurrentRPM() <= car.engine.getMaxRPM() - 500) {
                        gearUpButton.setEnabled(car.gBox.getCurrentGear() != car.gBox.getMaxGear());
                        gearDownButton.setEnabled(car.gBox.getCurrentGear() != 1);
                    } else {
                        gearUpButton.setEnabled(car.gBox.getCurrentGear() != car.gBox.getMaxGear());
                        gearDownButton.setEnabled(false);
                    }
                }
            } else {
                gearUpButton.setEnabled(false);
                gearDownButton.setEnabled(false);
            }
        }
        else {
            txtCarModel.setText("");
            txtCarLicenseNum.setText("");
            txtCarWeight.setText("");
            txtCarSpeed.setText("");
            txtEngineName.setText("");
            txtEnginePrice.setText("");
            txtEngineWeight.setText("");
            txtEngineRPM.setText("");
            txtGBName.setText("");
            txtGBPrice.setText("");
            txtGBWeight.setText("");
            txtGBGear.setText("");
            txtClutchName.setText("");
            txtClutchPrice.setText("");
            txtClutchWeight.setText("");
            txtClutchState.setText("");
            icon.setVisible(false);
            turnOnButton.setEnabled(false);
            turnOffButton.setEnabled(false);
            accButton.setEnabled(false);
            brakeButton.setEnabled(false);
            pressButton.setEnabled(false);
            releaseButton.setEnabled(false);
            gearUpButton.setEnabled(false);
            gearDownButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }
    public static void main(String[] args) {
        Engine engine = new Engine("RB26", 200, 1500, 8500);
        GearBox gearBox = new GearBox("AM5", 10, 799, 1, 6);
        Car c = new Car(false, "KPRXXX", "M2", 250, engine, gearBox);
        new CarGUI(c);

        JFrame frame = new JFrame("CarGUI");
        frame.setContentPane(new CarGUI(c).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}