public class Car extends Thread {
    private boolean currentState;
    private String licenseNumber;
    private String model;
    private double vMax;
    private int weight;
    Position currentPosition;
    double currentV;
    Engine engine = new Engine("2JZ", 200, 1500, 8000);
    GearBox gBox = new GearBox("AM6", 130, 699, 1,6);
    Position destination = new Position(0,0);
    protected int dt = 50;

    public Car(boolean currentState, String licenseNumber, String model, double vMax, Engine engine, GearBox gBox) {
        this.currentState = currentState;
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.vMax = vMax;
        this.currentPosition = new Position(0,0);
        this.currentV = 0.0;
        this.weight = engine.getWeight() + gBox.getWeight() + gBox.clutch.getWeight() + 1000;
        start();
    }

    public boolean isCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean currentState) {
        this.currentState = currentState;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getvMax() {
        return vMax;
    }

    public void setvMax(int vMax) {
        this.vMax = vMax;
    }

    public void turnOn() {
        currentState = true;
        this.engine.start();
        this.gBox.setCurrentGear(1);
        this.gBox.setCurrentGearboxRatio(1.1);
    }
    public void turnOff() {
        currentState = false;
        engine.stop();
    }
    public void goTo(double x_, double y_) {
        destination.setX(x_);
        destination.setY(y_);
    }
    public Position getCurrentPosition() {
        return currentPosition;
    }

    public double getCurrentV() {
        double v = engine.getCurrentRPM() * gBox.getCurrentGearboxRatio() * 0.015;
        if (v > vMax) {
            v = vMax;
        }

        if(currentPosition.getX() != destination.getX() || currentPosition.getY() != destination.getY()) {
            return v;
        }
        else {
            return 0;
        }

        //return engine.getCurrentRPM() * gBox.getCurrentGearboxRatio() * 0.02;
    }

    public void setCurrentV(double currentV) {
        this.currentV = currentV;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getTitle() {
        return this.model + " " + this.licenseNumber;
    }

    public void run(){
        while (true) {
            currentPosition.move(getCurrentV(), dt, destination.getX(), destination.getY()); //V, dt, dest
            if (currentPosition.getX() == destination.getX() && currentPosition.getY() == destination.getY() && this.isCurrentState()) {
                this.engine.setCurrentRPM(900);
                this.gBox.setCurrentGear(1);
                this.gBox.setCurrentGearboxRatio(1.1);
            }
            //System.out.println("X: " + currentPosition.getX() + " | Y: " + currentPosition.getY());
            //System.out.println("V: " + getCurrentV());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                return;
            }
        }
    }

    public static void main(String[] args) {
        //Car tmp = new Car(true, "KPRXXXX", "Fiat 126p", 120);
        //System.out.println(tmp.currentPosition.getX());
        //System.out.println(tmp.currentPosition.getY());
//
        //Car M2 = new Car(false, "K1NDLY", "M2", 300);
        //M2.turnOn();
        //System.out.println("State: " + M2.currentState);
        //System.out.println("Current RPM: " + M2.engine.getCurrentRPM());
        ////M2.goTo(100,1);
        //while(true) {
        //    M2.currentPosition.move(M2.getCurrentV(), M2.dt, M2.destination.getX(), M2.destination.getY()); //V, dt, dest
        //    System.out.println("X: " + M2.currentPosition.getX() + " | Y: " + M2.currentPosition.getY());
        //    System.out.println("V: " + M2.getCurrentV());
        //    try {
        //        Thread.sleep(50);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    /*if (M2.currentPosition.equals(M2.destination)){
        //        break;
        //    }*/
        //    /*if (M2.currentPosition.getX() == M2.destination.getX() && M2.currentPosition.getY() == M2.destination.getY()){
        //        //M2.setCurrentV(0);
        //        break;*/
        //    }
        //}
    }
}

//Grid Layout Manager
///JPanel - służy do ukłdania innych elementów
//Prawy->New->Gui form->Default manager