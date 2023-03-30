public class Engine extends Component {
    private int maxRPM;
    private int currentRPM;

    public Engine(String name, int weight, double price, int maxRPM) {
        super(name, weight, price);
        this.maxRPM = maxRPM;
    }

    public int getMaxRPM() {
        return maxRPM;
    }

    public void setMaxRPM(int maxRPM) {
        this.maxRPM = maxRPM;
    }

    public int getCurrentRPM() {
        return currentRPM;
    }

    public void setCurrentRPM(int currentRPM) {
        this.currentRPM = currentRPM;
    }

    public void start(){
        setCurrentRPM(900);
    }
    public void stop(){
        setCurrentRPM(0);
    }
    public void increaseRPM(){
        setCurrentRPM(getCurrentRPM() + 100);
    }
    public void decreaseRPM(){
        setCurrentRPM(getCurrentRPM() - 100);
    }
}
