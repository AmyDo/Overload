package components;

public class PowerSource  extends Component {
    private int draw;


    public PowerSource(String name) {
        super(name);
        this.draw=0;    //set draw=0;
    }


    /**
     * What Component is feeding power to this Component.
     *
     * @return source component
     */
    @Override
    protected Component getSource() {
        return null;
    }
    /**
     * Display this (sub)tree vertically, with indentation
     */
    @Override
    public void display() {

    }



}
