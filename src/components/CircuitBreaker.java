package components;

import java.util.HashSet;

public class CircuitBreaker extends Component {
    private Component source;
    private int limit;
    private boolean on;
    HashSet<Component> hset= new HashSet<>();  //create an unmodifiable collections of loads


    /**
     * constructor
     * @param name of this circuit breaker
     * @param source to draw current from
     * @param limit max rated load
     */
    public CircuitBreaker(String name, Component source, int limit) {
        super(name);
        this.source= source;
        source.attach(this);       //attach this to its source
        if (source instanceof CircuitBreaker){
            if(source.engaged==true && ((CircuitBreaker) source).isSwitchOn()==true){
                this.engaged= true;
            }else{
                this.engaged=false;
            }
        }else{
            this.engaged=false;
        }
        this.limit= limit;
        this.on=false;
    }

    public void turnOn(){
        this.on=true;
    }
    public void turnOff(){
        this.on=false;
    }
    public boolean isSwitchOn(){
        return this.on;
    }
    public int getLimit(){
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
     * Display this (sub)tree vertically, with indentation
     */
    @Override
    protected void display() {

    }


}
