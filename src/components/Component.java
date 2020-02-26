package components;

import java.util.Collection;
import java.util.HashSet;

public abstract class Component {
    protected String name;
    protected HashSet<Component> hset;



    public Component(String name) {
        this.name = name;
        hset= new HashSet<>();
    }

    /**
     * Add a new load to this component
     *
     * @param newLoad -the new component to be added
     */
    protected abstract void addLoad(Component newLoad);

    /**
     * add a new load (sth that draws current to this Component)
     *
     * @param load- the component to be 'pluged in'
     */
    protected abstract void attach(Component load);

    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta- the number of amp by which to raise (+) or lower(-) the draw
     */
    protected abstract void changeDraw(int delta);

    /**
     * This component tells its loads that they can no longer acts as a source that they
     * will no longer get any current
     */
    protected abstract void disengage();

    /**
     * Inform all Components to which this Component acts as a source
     * that they will no longer get any current
     */
    protected abstract void disensageLoads();

    /**
     * Display this (sub)tree vertically, with indentation
     */
    protected abstract void display();

    /**
     * the source for this component is now being empowered.
     */
    protected abstract void engage();

    /**
     * Is is Component currently being fed power?
     *
     * @return true or false
     */
    protected abstract boolean engaged();

    /**
     * Inform all Components to which this Component acts as a source
     * that they may not draw current
     */
    protected abstract void engageLoads();

    /**
     * Find out how much current this current is drawing.
     *
     * @return interger.
     */
    protected abstract int getDraw();

    /**
     * What loads are attached under this component
     *
     * @return collection of loads
     */
    protected abstract Collection<Component> getLoads();

    /**
     * Get component's name
     *
     * @return string of name
     */
    protected String getName() {
        return name;
    }

    /**
     * What Component is feeding power to this Component.
     *
     * @return source component
     */
    protected abstract Component getSource();

    /**
     * Change this Component draw to the given value
     *
     * @param draw to be set to.
     */
    protected abstract void setDraw(int draw);

    /**
     * Describe a component in the manner of Reporter.identify(Component)
     *
     * @return
     */
    @Override
    public String toString() {
        return "Component{}";
    }


}
