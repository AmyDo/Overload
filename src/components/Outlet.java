package components;

import java.util.HashSet;

/**
 * This class represents an outlet component, and its functionaries.
 */
public class Outlet extends Component {
    private Component source;

    /**
     * construct the outlet component.
     * @param name   of this outlet
     * @param source that this outlet drw from
     */
    public Outlet(String name, Component source) {
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
        this.draw = 0;
        Reporter.report(source, this, Reporter.Msg.ATTACHING);
        this.switchable = false;

    }

    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
        this.getSource().engaged = true;
        this.engaged = true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
        for (Component comp : hset) {
            comp.engage();
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
        for (Component comp : hset) {
            System.out.println("              +" + comp.toString());
            comp.display();
        }
    }


    /**
     * toggle the switchable component.
     */
    @Override
    public void toggle() {

    }

    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta- the number of amp by which to raise (+) or lower(-) the draw
     */
    @Override
    protected void changeDraw(int delta) {
        super.changeDraw(delta);
        this.getSource().changeDraw(delta);
    }


}
