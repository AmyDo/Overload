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
        if (!this.isSwitchOn()){
            this.on = true;
            Reporter.report(this, Reporter.Msg.SWITCHING_ON);
            if (this.engaged()) {
                this.changeDraw(RATING);
            }
        }
    }

    public void turnOff() {
        if(this.isSwitchOn()){
            this.on = false;
            Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
            this.changeDraw(-this.getDraw());
        }
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
        this.engaged = true;
        Reporter.report(this, Reporter.Msg.ENGAGING);

        if (this.isSwitchOn()) {
            this.changeDraw(RATING);
        }
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
     @Override
    protected void changeDraw(int delta) {
         super.changeDraw(delta);
         this.getSource().changeDraw(delta);
     }

    /**
     * This component tells its loads that they can no longer acts as a source that they
     * will no longer get any current
     */
    @Override
    protected void disengage() {
        super.disengage();
        this.changeDraw(-this.getDraw());
    }
}


