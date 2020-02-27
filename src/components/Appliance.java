package components;

import java.util.HashSet;

public class Appliance extends Component {
    private Component source;
    private int RATING;
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
            if (source.engaged() && ((CircuitBreaker) source).isSwitchOn()) {
                this.engaged = true;

            } else {
                this.engaged = false;
            }
        } else {    //if the source is not a switchable component.
            if (source.engaged()) {
                this.engaged = true;

            } else {
                this.engaged = false;
            }
        }
        this.RATING = rating;
        this.on = false;
        Reporter.report(this, Reporter.Msg.CREATING);
        Reporter.report(source, this, Reporter.Msg.ATTACHING);
    }

    public void turnOn() {
        this.on = true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        this.source.changeDraw(RATING);
        Reporter.report(this.getSource(), Reporter.Msg.DRAW_CHANGE , source.getDraw());
    }

    public void turnOff() {
        this.on = false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        this.source.changeDraw(-RATING);
        Reporter.report(this.getSource(), Reporter.Msg.DRAW_CHANGE, this.RATING);
    }

    public boolean isSwitchOn() {
        return this.on;
    }
//
//    public int getRating() {
//        return this.RATING;
//    }

    public String getRating() {
        return Integer.toString(this.RATING);
    }




    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
        this.getSource().engaged = true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
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
     * Display this (sub)tree vertically, with indentation
     */
    @Override
    protected void display(){
    }

    @Override
    protected String printComponent(HashSet<Component> hset) {
        String str="";
        if (hset.isEmpty()){
            return null;
        }else{
            for (Component comp : hset){
                str= "+"+ Reporter.identify(comp);
                printComponent(comp.hset);
            }


        }
        return str;
    }


}


