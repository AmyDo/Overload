package components;

import java.util.HashSet;

public class Outlet extends Component {
    private Component source;

    /**
     * constructor
     *
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
        Reporter.report(this, Reporter.Msg.CREATING);
        Reporter.report(source, this, Reporter.Msg.ATTACHING);
    }

    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
        this.getSource().engaged = true;
        this.engaged = true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
        for(Component comp: hset){
           comp.engage();
        }
    }
    /**
     * What Component is feeding power to this Component.
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
        System.out.println(printComponent(this.hset));

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
        this.getSource().changeDraw(delta);
    }



}
