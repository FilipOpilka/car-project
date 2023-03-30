public class GearBox extends Component {
    private int currentGear;
    private int numOfGears;
    private double currentGearboxRatio = 1.1;
    private int maxGear;
    Clutch clutch;

    public GearBox(String name, int weight, double price, int currentGear, int maxGear) {
        super(name, weight, price);
        this.currentGear = currentGear;
        this.maxGear = maxGear;
        //this.numOfGears = numOfGears;
        //this.currentGearboxRatio = currentGearboxRatio;
        this.clutch = new Clutch("SACHS", 10, 799, false);
    }

    public int getCurrentGear() {
        return currentGear;
    }

    public void setCurrentGear(int currentGear) {
        this.currentGear = currentGear;
    }

    public int getNumOfGears() {
        return numOfGears;
    }

    public void setNumOfGears(int numOfGears) {
        this.numOfGears = numOfGears;
    }

    public double getCurrentGearboxRatio() {
        return currentGearboxRatio;
    }

    public void setCurrentGearboxRatio(double currentGearboxRatio) {
        this.currentGearboxRatio = currentGearboxRatio;
    }

    public void increaseGear() {
        this.currentGear ++;
        this.currentGearboxRatio += 0.25;
    }
    public void decreaseGear() {
        this.currentGear --;
        this.currentGearboxRatio -= 0.25;
    }

    public int getMaxGear() {
        return maxGear;
    }

    public void setMaxGear(int maxGear) {
        this.maxGear = maxGear;
    }
}
