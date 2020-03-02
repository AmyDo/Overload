package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * This abstract class represent the backbone of all components. It contains all states and
 * methods that will be on the subclasses
 * @Author: Amy do
 */
public abstract class Component {
    protected String name;
    protected boolean engaged;
    protected ArrayList<Component> hset;
    protected int draw;
    protected boolean switchable;
    protected boolean on;

    /**
     * constructor
     *
     * @param name of this component.
     */
    public Component(String name) {
        this.name = name;
        this.hset = new ArrayList<>();      //create set of loads
        this.draw = 0;
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    /**
     * Add a new load to this component
     *
     * @param newLoad -the new component to be added
     */
    protected void addLoad(Component newLoad) {
        this.attach(newLoad);
    }

    /**
     * add a new load (sth that draws current to this Component)
     * If this component is engaged, the new load becomes engaged.
     *
     * @param load- the component to be 'pluged in'
     */
    protected void attach(Component load) {
        this.hset.add(load);
    }

    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta- the number of amp by which to raise (+) or lower(-) the draw
     */
    protected void changeDraw(int delta) {
        this.draw += delta;
        if (delta != 0) {

            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);

        }
    }


    /**
     * This component tells its loads that they can no longer acts as a source that they
     * will no longer get any current
     */
    protected void disengage() {
        this.engaged = false;
        Reporter.report(this, Reporter.Msg.DISENGAGING);
    }

    /**
     * Inform all Components to which this Component acts as a source
     * that they will no longer get any current
     *
     */
    protected void disengageLoads() {
        for (Component comp : this.hset) {
            comp.disengage();
            comp.disengageLoads();
        }
    }


    /**
     * the source for this component is now being empowered.
     */
    public abstract void engage();

    /**
     * Is is Component currently being fed power?
     */
    protected boolean engaged() {
        return this.engaged;
    }

    /**
     * Inform all Components to which this Component acts as a source
     * that they may not draw current
     */
    protected void engageLoads() {
        for (Component comp : this.hset) {
            comp.engage();
        }
    }

    /**
     * Find out how much current this current is drawing.
     *
     * @return interger.
     */
    protected int getDraw() {
        return this.draw;
    }

    /**
     * What loads are attached under this component
     *
     * @return collection of loads
     */
    protected HashSet<Component> getLoads() {
        return (HashSet<Component>) Collections.unmodifiableCollection(this.hset);
    }

    /**
     * Get component's name
     *
     * @return string of name
     */
    protected String getName() {
        return this.name;
    }

    /**
     * Change this Component draw to the given value
     *
     * @param draw to be set to.
     */
    protected void setDraw(int draw) {
        this.draw = draw;
    }

    /**
     * Describe a component in the manner of Reporter.identify(Component)
     *
     * @return
     */
    @Override
    public String toString() {
        return Reporter.identify(this);
    }

    /**
     * What Component is feeding power to this Component.
     *
     * @return source component
     */
    protected abstract Component getSource();

    /**
     * Display this (sub)tree vertically, with indentation
     */

    public void display() {
        System.out.println("+" + this.toString());
        for (Component comp : this.hset) {
            System.out.println( "    +"+comp.toString());
            comp.display();
        }
    }

    /**
     * toggle the switchable component
     */
     public abstract void toggle();

    /**
     * checks if the component is switchable or not.
     * @return
     */
    public boolean getSwitchable(){
        return this.switchable;
    }

}
