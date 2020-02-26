package components;

import java.util.Collection;
import java.util.HashSet;

public class Outlet extends Component {
    private Component source;
    private int draw;   //draw equals to the sum of draw from all connected appliances

    /**
     *
     * @param name
     * @param source
     */
    public Outlet(String name, Component source) {
        super(name);
        this.source= source;
        source.attach(this);       //attach this to its source
        if (source.engaged()==true){
             //if the source is engaged, this component will have power
        }
        this.draw=0;
    }



    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta - the number of amp by which to raise (+) or lower(-) the draw
     */
    @Override
    protected void changeDraw(int delta) {
        this.draw+= delta;
    }

    /**
     * This component tells its loads that they can no longer acts as a source that they
     * will no longer get any current
     */
    @Override
    protected void disengage() {

    }

    /**
     * Inform all Components to which this Component acts as a source
     * that they will no longer get any current
     */
    @Override
    protected void disensageLoads() {

    }

    /**
     * Display this (sub)tree vertically, with indentation
     */
    @Override
    protected void display() {

    }

    /**
     * the source for this component is now being empowered.
     */
    @Override
    protected void engage() {

    }

    /**
     * Is is Component currently being fed power?
     *
     * @return true or false
     */
    @Override
    protected boolean engaged() {
        return false;
    }

    /**
     * Inform all Components to which this Component acts as a source
     * that they may not draw current
     */
    @Override
    protected void engageLoads() {

    }

    /**
     * Find out how much current this current is drawing.
     *
     * @return interger.
     */
    @Override
    protected int getDraw() {

        return this.draw;
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
     * Change this Component draw to the given value
     *
     * @param draw to be set to.
     */
    @Override
    protected void setDraw(int draw) {
        this.draw=draw;
    }
}
