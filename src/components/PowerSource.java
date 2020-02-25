package components;

import java.util.Collection;

public class PowerSource  extends Component {
    String name;

    public PowerSource(String name) {
        this.name = name;
    }

    /**
     * Add a new load to this component
     *
     * @param newLoad -the new component to be added
     */
    @Override
    protected void addLoad(Component newLoad) {

    }

    /**
     * add a new load (sth that draws current to this Component)
     *
     * @param load - the component to be 'pluged in'
     */
    @Override
    protected void attach(Component load) {

    }

    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta - the number of amp by which to raise (+) or lower(-) the draw
     */
    @Override
    protected void changeDraw(int delta) {

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
    public void display() {

    }

    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {

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
        return 0;
    }

    /**
     * What loads are attached under this component
     *
     * @return collection of loads
     */
    @Override
    protected Collection<Component> getLoads() {
        return null;
    }

    /**
     * Get component's name
     *
     * @return string of name
     */
    @Override
    protected String getName() {
        return null;
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
     * Change this Component draw to the given value
     *
     * @param draw to be set to.
     */
    @Override
    protected void setDraw(int draw) {

    }
}
