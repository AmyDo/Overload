package components;

public class Outlet extends Component {
    private Component source;
    private int draw;   //draw equals to the sum of draw from all connected appliances

    /**
     *constructor
     * @param name of this outlet
     * @param source that this outlet drw from
     */
    public Outlet(String name, Component source) {
        super(name);
        this.source= source;
        source.attach(this);       //attach this to its source
        if (source instanceof CircuitBreaker){
            if(source.engaged==true && ((CircuitBreaker) source).isSwitchOn()==true){
                super.engaged= true;
            }else{
                super.engaged=false;
            }
        }else{
            super.engaged=false;
        }
        this.draw = 0;
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
    protected void display() {

    }



}
