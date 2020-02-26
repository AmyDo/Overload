package components;

public class PowerSource  extends Component {
    private int draw;


    public PowerSource(String name) {
        super(name);
        this.draw=0;    //set draw=0;
    }

    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
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
     * Describe a component in the manner of Reporter.identify(Component)
     *
     * @return
     */
//    @Override
//    public String toString() {
//        return "+PowerSource "+ this.getName()+ " (" +" draw "+
//                String.valueOf(this.draw)+")";
//    }
    /**
     * Display this (sub)tree vertically, with indentation
     */
    public void display(){
        for (Component comp: this.hset){
            System.out.println(comp.toString());

        }
    }
}
