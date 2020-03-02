package components;

import java.util.HashSet;

public class CircuitBreaker extends Component {
    private Component source;
    private int limit;
    private boolean on;
    private int prevDelta;


    /**
     * constructor
     *
     * @param name   of this circuit breaker
     * @param source to draw current from
     * @param limit  max rated load
     */
    public CircuitBreaker(String name, Component source, int limit) {
        super(name);
        this.source = source;
        source.attach(this);       //attach this to its source
        if (source instanceof CircuitBreaker) {
            if (source.engaged == true && ((CircuitBreaker) source).isSwitchOn() == true) {
                this.engaged = true;
            } else {
                this.engaged = false;
            }
        } else {
            this.engaged = false;
        }

        this.limit = limit;
        this.on = false;
        this.switchable = true;
        Reporter.report(source, this, Reporter.Msg.ATTACHING);

    }

    public void turnOn() {
        this.on = true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        for (Component comp : hset) {
            comp.engage();
        }
    }

    public void turnOff() {
        this.on = false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        this.source.changeDraw(-prevDelta);
        // this.source.engaged=false;
        this.disengageLoads();
    }


    public boolean isSwitchOn() {
        return this.on;
    }

    public String getLimit() {
        return Integer.toString(limit);
    }


    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
        this.getSource().engaged = true;
        this.engaged = true;
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
    public void display() {
        for (Component comp : hset) {
            System.out.println("          +" + comp.toString());
            comp.display();
        }

    }

    @Override
    public void toggle() {
        if (this.isSwitchOn()) {
            this.turnOff();
        } else {
            this.turnOn();
        }
    }


    @Override
    protected void changeDraw(int delta) {
        this.draw += delta;
        if (delta != 0 && this.draw <= this.limit) {
            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);

        }
        this.prevDelta = this.draw - delta;
        if (this.draw > this.limit && this.isSwitchOn()) {  //if the current draw exceed the limit. blow up.
            Reporter.report(this, Reporter.Msg.BLOWN, this.getDraw());
            this.turnOff();
        }
        if (this.isSwitchOn())
            this.getSource().changeDraw(delta);
    }
}
