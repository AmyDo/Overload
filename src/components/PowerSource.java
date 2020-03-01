package components;

import java.util.HashSet;

public class PowerSource extends Component {
    private boolean created;
    private int prevDelta;


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

    @Override
    public void toggle() {
    }
}


