package components;

import java.util.Collections;
import java.util.HashSet;

public abstract class Component {
    protected String name;
    protected boolean engaged;
    protected HashSet<Component> hset;
    protected int draw;

    /**
     * constructor
     * @param name of this component.
     */
    public Component(String name) {
        this.name = name;
        this.hset= new HashSet<>();      //create set of loads
        this.draw=0;
    }

    /**
     * Add a new load to this component
     *
     * @param newLoad -the new component to be added
     */
    protected void addLoad(Component newLoad){
        this.attach(newLoad);
    }

    /**
     * add a new load (sth that draws current to this Component)
     * If this component is engaged, the new load becomes engaged.
     * @param load- the component to be 'pluged in'
     */
    protected void attach(Component load){

        this.hset.add(load);
    }

    /**
     * Change the amount of current passing through this Component.
     *
     * @param delta- the number of amp by which to raise (+) or lower(-) the draw
     */
    protected void changeDraw(int delta){
        this.draw+= delta;
    }

    /**
     * This component tells its loads that they can no longer acts as a source that they
     * will no longer get any current
     */
    protected void disengage(){
       this.engaged=false;
    }

    /**
     * Inform all Components to which this Component acts as a source
     * that they will no longer get any current
     */
    protected void disensageLoads(){
        for (Component comp: this.hset){
            comp.disengage();
        }
    }

    /**
     * the source for this component is now being empowered.
     */
    public abstract void engage();

    /**
     * Is is Component currently being fed power?
     *
     */
    protected boolean engaged(){
        return this.engaged;
    }

    /**
     * Inform all Components to which this Component acts as a source
     * that they may not draw current
     */
    protected void engageLoads(){
        for (Component comp: this.hset){
            comp.engage();
        }
    }

    /**
     * Find out how much current this current is drawing.
     *
     * @return interger.
     */
    protected int getDraw(){
        return this.draw;
    }

    /**
     * What loads are attached under this component
     *
     * @return collection of loads
     */
    protected HashSet<Component> getLoads(){
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
    protected void setDraw(int draw){

        this.draw= draw;
    }

    /**
     * Describe a component in the manner of Reporter.identify(Component)
     *
     * @return
     */
    @Override
    public String toString(){
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
    protected abstract void display();

    protected abstract String printComponent(HashSet<Component> hset);

}
