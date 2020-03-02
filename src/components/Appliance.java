package components;

import java.util.HashSet;

/**
 * This class represents a switchable Appliance component, and its functionaries.
 * @author: Amy Do
 */
public class Appliance extends Component {
    private Component source;
    private int RATING;
    private boolean on;


    /**
     * construct the Appliance component
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
        Reporter.report(source, this, Reporter.Msg.ATTACHING);
        this.RATING = rating;
        this.on = false;
        this.switchable=true;

    }

    /**
     * Turns on the appliance
     */
    public void turnOn() {
        if (!this.isSwitchOn()){
            this.on = true;
            Reporter.report(this, Reporter.Msg.SWITCHING_ON);
            if (this.engaged()) {
                this.changeDraw(RATING);
            }
        }
    }

    /**
     * turns off the appliance
     */
    public void turnOff() {
        if(this.isSwitchOn()){
            this.on = false;
            Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
            this.changeDraw(-this.getDraw());
        }
    }

    /**
     * Checks if the appliance is on or off.
     * @return boolean value
     */
    public boolean isSwitchOn() {
        return this.on;
    }

    /**
     * gets the rating of the appliance
     * @return
     */
    public String getRating() {
        return Integer.toString(this.RATING);
    }


    /**
     * This component is now being empowered.
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
    public void display() {

    }

    /**
     * toggle the switchable component
     */
    @Override
    public void toggle() {
        if (this.isSwitchOn()){
            this.turnOff();
        }else{
            this.turnOn();
        }
    }

    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta- the number of amp by which to raise (+) or lower(-) the draw
     */
    @Override
    protected void changeDraw(int delta) {
         //super.changeDraw(delta);
        this.draw += delta;
//        if (delta != 0) {
//            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
//
//        }
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


