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
        Reporter.report(this, Reporter.Msg.CREATING);
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
    protected void display() {
        System.out.println();
    }

    @Override
    protected String printComponent(HashSet<Component> hset) {
        String str = "";
        if (hset.isEmpty()) {
            return null;
        } else {
            for (Component comp : hset) {
                str = "+" + Reporter.identify(comp);
                printComponent(comp.hset);
            }
        }
        return str;
    }


    @Override
    protected void changeDraw(int delta) {
        super.changeDraw(delta);
        this.prevDelta = this.draw - delta;
        if (this.draw > this.limit) {  //if the current draw exceed the limit. blow up.
            Reporter.report(this, Reporter.Msg.BLOWN, this.getDraw());
            this.turnOff();

        }
        if (this.getSource() != null) {      //if the
            this.getSource().changeDraw(delta);
        }
    }



}
