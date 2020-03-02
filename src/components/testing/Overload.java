package components.testing;

import components.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * DESCRIPTION
 *
 * @author Amy Do
 */
public class Overload {
    HashMap<String, Component> hmap = new HashMap<String, Component>();
    ArrayList<String> commandList = new ArrayList<String>();
    ArrayList<String> powerSourceArrayList = new ArrayList<>();
    ArrayList<String> typeArray = new ArrayList<>();


    public static final int BAD_ARGS = 1;
    public static final int FILE_NOT_FOUND = 2;
    public static final int BAD_FILE_FORMAT = 3;
    public static final int UNKNOWN_COMPONENT = 4;
    public static final int REPEAT_NAME = 5;
    public static final int UNKNOWN_COMPONENT_TYPE = 6;
    public static final int UNKNOWN_USER_COMMAND = 7;
    public static final int UNSWITCHABLE_COMPONENT = 8;

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String[] NO_STRINGS = new String[0];
    private static final String PROMPT = "? ";

    static {
        Reporter.addError(
                BAD_ARGS, "Usage: java components.Overload <configFile>");
        Reporter.addError(FILE_NOT_FOUND, "Config file not found");
        Reporter.addError(BAD_FILE_FORMAT, "Error in config file");
        Reporter.addError(
                UNKNOWN_COMPONENT,
                "Reference to unknown component in config file"
        );
        Reporter.addError(
                REPEAT_NAME,
                "Component name repeated in config file"
        );
        Reporter.addError(
                UNKNOWN_COMPONENT_TYPE,
                "Reference to unknown type of component in config file"
        );
        Reporter.addError(
                UNKNOWN_USER_COMMAND,
                "Unknown user command"
        );
    }

    private static Reporter Support;


    public Overload(String filename, String filename2) {
        readFile(filename);
        readCommandFile(filename2);

    }

    public static void main(String[] args) {
        System.out.println("Overload Project, CS2");
//        if (args.length != 1) {
//            System.out.println("Please Enter correct filename");
//            return;
//        } else {
//            new Overload(args[0]).run();
        new Overload("config4.txt", "config4_inA.txt");
        //  }
    }
    public void run() {
    }
    public void readFile(String filename) {
        try (Scanner configFile = new Scanner((new File(filename)))) {
            int count = 0;
            while (configFile.hasNextLine()) {
                //process the line in the tex
                String[] line = configFile.nextLine().split(" ");
                processLine(line);
                count++;
            }
            System.out.println(count + " components created.");
            System.out.println("Starting up the main circuit(s): ");
            System.out.println("PowerSource Home(draw 0): powering up");
            for (String ps : powerSourceArrayList) {   //get the name of the power-source
                hmap.get(ps).engage();
            }
        } catch (FileNotFoundException e) {
            //Support.usageError(FILE_NOT_FOUND);
            Reporter.usageError(FILE_NOT_FOUND);
        }
    }

    /**
     * this method proccess each line and them into the hashmap.
     *
     * @param line to be proccessed
     */
    public void processLine(String[] line) {
        typeArray.add("Appliance");
        typeArray.add("CircuitBreaker");
        typeArray.add("Outlet");
        typeArray.add("PowerSource");
        if (!typeArray.contains(line[0])) {
           // Support.usageError(UNKNOWN_COMPONENT_TYPE);
            Reporter.usageError(UNKNOWN_COMPONENT_TYPE);
        } else {
            if (line[0].equals("PowerSource")) {
                hmap.put(line[1], new PowerSource(line[1]));
                powerSourceArrayList.add(line[1]);    //add all the name of power source to the arraylist
            } else if (line[0].equals("CircuitBreaker")) {
                hmap.put(line[1], new CircuitBreaker(line[1], hmap.get(line[2]), Integer.parseInt(line[3])));
            } else if (line[0].equals("Outlet")) {
                hmap.put(line[1], new Outlet(line[1], hmap.get(line[2])));
            } else {
                hmap.put(line[1], new Appliance(line[1], hmap.get(line[2]), Integer.parseInt(line[3])));
            }
        }
    }

    public void readCommandInput(String input){

    }
    public void readCommandFile(String filename) {
        try (Scanner configFile = new Scanner((new File(filename)))) {
            int count = 0;
            while (configFile.hasNextLine()) {
                String[] line = configFile.nextLine().split(" ");
                proccessCommand(line);
                count++;
            }

        } catch (FileNotFoundException e) {
            //Support.usageError(FILE_NOT_FOUND);
            Reporter.usageError((FILE_NOT_FOUND));
        }
    }


    public void proccessCommand(String[] line) {
        commandList.add("connect");
        commandList.add("toggle");
        commandList.add("display");
        commandList.add("quit");
        if (!commandList.contains(line[0])) {
         //   Support.usageError(UNKNOWN_USER_COMMAND);
            Reporter.usageError(UNKNOWN_USER_COMMAND);

        }
        if (line[0].equals("display")) {
            System.out.println(" ?  -> display[]");
            for (String ps : powerSourceArrayList) {  //get the keys of the powersource
                hmap.get(ps).display();
            }

        } else if (line[0].equals("toggle")) {
            System.out.println(" ?  -> toggle[" + line[1] + "]");

            if (hmap.containsKey(line[1])){  //check if the
                if (hmap.get(line[1]).getSwitchable()) {    //check if the component is Circuit breaker or Appliance.
                    hmap.get(line[1]).toggle();
                } else {
                    //Support.usageError(UNSWITCHABLE_COMPONENT);
                    Reporter.usageError(UNSWITCHABLE_COMPONENT);
                }
            }else{
                //Support.usageError(UNKNOWN_COMPONENT);
                Reporter.usageError(UNKNOWN_COMPONENT);

            }

        } else if (line[0].equals("connect")) {
            System.out.println(" ?  -> connect[ ");
            String[] newLine= new String[line.length-1];  //create new array
            for( int i=0; i< newLine.length;i++){
                newLine[i]=line[i+1];
            }
            if (hmap.containsKey(line[3])) {   //check if the source is already existed or not.
                processLine(newLine);      //proccess the new component.

            } else {
                Reporter.usageError(UNKNOWN_COMPONENT);

            }
        } else if (line[0].equals("quit")) {
            System.out.println(" ?  -> quit");
            System.exit(1);
        }
    }


}
