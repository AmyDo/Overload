package components;

import java.util.HashSet;

public class PowerSource  extends Component {
    private boolean created;



    public PowerSource(String name) {
        super(name);
        this.engaged=true;
        this.created=true;
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    /**
     * the source for this component is now being empowered.
     */
    @Override
    public void engage() {
        this.engaged=true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
        for(Component comp: hset){
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
     * Display this (sub)tree vertically, with indentation
     */
    @Override
    public void display() {
        for (Component comp: this.hset){
            Reporter.report(this, comp, Reporter.Msg.ATTACHING);
        }
    }

    @Override
    protected String printComponent(HashSet<Component> hset) {
        String str="";
        if (hset.isEmpty()){
            return null;
        }else{
            for (Component comp : hset){
                str= "+"+ Reporter.identify(comp);
                printComponent(comp.hset);
            }


        }
        return str;
    }
}
