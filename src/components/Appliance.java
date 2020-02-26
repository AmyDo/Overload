package components;

public class Appliance extends Component {
    private Component source;
    private static int RATING;
    private boolean on;

    /**
     * constructor
     *
     * @param name   of this appliance
     * @param source of this appliance.
     * @param rating rated draw(unchanged)
     */
    public Appliance(String name, Component source, int rating) {
        super(name);
        this.source = source;
        source.attach(this);       //attach this componnent to its source
        if (source instanceof CircuitBreaker) {    //if the source is swichable (circuitbreaker, Appliance)
            if (source.engaged() == true && ((CircuitBreaker) source).isSwitchOn() == true) {
                this.engaged = true;
            } else {
                this.engaged = false;
            }
        } else {    //if the source is not a switchable component.
            if (source.engaged() == true) {
                this.engaged = true;
            } else {
                this.engaged = false;
            }
        }
        this.RATING = rating;
        this.on = false;
    }

    public void turnOn() {
        this.on = true;
    }

    public void turnOff() {
        this.on = false;
    }

    public boolean isSwitchOn() {
        return this.on;
    }

    public int getRating() {
        return this.RATING;
    }


    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
        this.getSource().engaged = true;
    }


    /**
     * What Component is feeding power to this Component.
     *
     * @return source component
     */
    @Override
    protected Component getSource() {
        return this.source;
    }

    /**
     * Describe a component in the manner of Reporter.identify(Component)
     *
     * @return
     */
//    @Override
//    public String toString() {
//        StringBuilder str= new StringBuilder();
//        if (this.isSwitchOn()==false){
//            str.append("off");
//        }else{
//            str.append("on");
//        }
//        return "+Appliance "+ this.getName()+ " (" + str +"; rating "+
//                String.valueOf(this.RATING)+ ")";
//    }
    /**
     * Display this (sub)tree vertically, with indentation
     */
    protected void display(){
        for (Component comp: this.hset){
            System.out.println(comp.toString());
        }
    }
}


