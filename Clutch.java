public class Clutch extends Component {
    private boolean clutchState;    //true - pressed

    public Clutch(String name, int weight, double price, boolean clutchState) {
        super(name, weight, price);
        this.clutchState = clutchState;
    }

    public String isClutchState() {
        if (clutchState){
            return "Pressed";
        }
        else {
            return "Released";
        }
    }

    public void setClutchState(boolean clutchState) {
        this.clutchState = clutchState;
    }

    public boolean getClutchState() {
        return clutchState;
    }

    public void press() {
        setClutchState(true);
    }

    public void release() {
        setClutchState(false);
    }
}
