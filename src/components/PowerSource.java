package components;

import java.util.HashSet;

/**
 * This class represents a power source component, and its functionaries.
 * @author Amy Do
 */
public class PowerSource extends Component {
    private boolean created;
    private int prevDelta;

    /**
     * constructor
     * @param name string
     */
    public PowerSource(String name) {
        super(name);
        this.engaged = true;
        this.created = true;
        this.switchable=false;
    }

    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
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
        return null;
    }

    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta- the number of amp by which to raise (+) or lower(-) the draw
     */
    @Override
    protected void changeDraw(int delta) {
            super.changeDraw(delta);


    }

    /**
     * Display this (sub)tree vertically, with indentation
     */
    @Override
    public void display() {
        super.display();

    }

    /**
     * toggle the switchable component.
     */
    @Override
    public void toggle() {
    }
}


