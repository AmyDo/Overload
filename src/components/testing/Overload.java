package components.testing;

import components.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PushbackInputStream;
import java.lang.reflect.Array;
import java.util.*;

/**
 * DESCRIPTION: this program simulates  the flow of electricity through the wiring in a home
 *
 * @author Amy Do
 */
public class Overload {
    HashMap<String, Component> hmap = new HashMap<String, Component>();
    ArrayList<String> commandList = new ArrayList<String>();
    ArrayList<String> powerSourceArrayList = new ArrayList<>();
    ArrayList<String> typeArray = new ArrayList<>();
    public int count;


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


    /**
     * Class constructor
     *
     * @param filename string
     */
    public Overload(String filename) {
        readFile(filename);
        readCommandInput();
        //  readCommandFile(command);
        // readCommandInput(command);

    }

    /**
     * The main program. This takes in command line argument and user input to run the program.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Overload Project, CS2");
        if (args.length == 0) {
            Reporter.usageError(FILE_NOT_FOUND);
            return;
        } else if (args.length > 1) {
            Reporter.usageError(BAD_ARGS);
        } else {
            new Overload(args[0]);
//        new Overload("config4.txt", "config4_inA.txt");
        }
    }




    /**
     * this method reads each line and call methods to process it
     *
     * @param filename
     */
    public void readFile(String filename) {
        try (Scanner configFile = new Scanner((new File(filename)))) {
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
            Reporter.usageError(FILE_NOT_FOUND);
        }
    }

    /**
     * this method processes each line and add them into the hashmap.
     *
     * @param line to be proccessed
     */
    public void processLine(String[] line) {
        typeArray.add("Appliance");
        typeArray.add("CircuitBreaker");
        typeArray.add("Outlet");
        typeArray.add("PowerSource");
        if (!typeArray.contains(line[0])) {
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

    /**
     * Takes in command input and takes action based on it.
     *
     * @param
     */
    public void readCommandInput() {
        while (true) {
            System.out.print(PROMPT+ "->");
            Scanner scanIn = new Scanner(System.in);
            String input = scanIn.nextLine();
            String[] line = input.split(" ");
            proccessCommand(line);
        }

    }

    /**
     * This method reads command file and takes action based on it
     *
     * @param filename string
     */
    public void readCommandFile(String filename) {
        try (Scanner configFile = new Scanner((new File(filename)))) {
            int count = 0;
            while (configFile.hasNextLine()) {
                String[] line = configFile.nextLine().split(" ");
                proccessCommand(line);
                count++;
            }
        } catch (FileNotFoundException e) {
            Reporter.usageError((FILE_NOT_FOUND));
        }
    }

    /**
     * This method performs action based on particular command.
     * if the
     *
     * @param line command to be process
     */
    public void proccessCommand(String[] line) {
        commandList.add("connect");
        commandList.add("toggle");
        commandList.add("display");
        commandList.add("quit");
        if (!commandList.contains(line[0])) {
            Reporter.usageError(UNKNOWN_USER_COMMAND);
        }


        if (line[0].equals("display")) {
            for (String ps : powerSourceArrayList) {  //get the keys of the powersource
                hmap.get(ps).display();
            }

        } else if (line[0].equals("toggle")) {

            if (hmap.containsKey(line[1])) {  //check if the
                if (hmap.get(line[1]).getSwitchable()) {    //check if the component is Circuit breaker or Appliance.
                    hmap.get(line[1]).toggle();
                } else {
                    Reporter.usageError(UNSWITCHABLE_COMPONENT);
                }
            } else {
                //Support.usageError(UNKNOWN_COMPONENT);
                Reporter.usageError(UNKNOWN_COMPONENT);

            }

        } else if (line[0].equals("connect")) {
            String[] newLine = new String[line.length - 1];  //create new array
            for (int i = 0; i < newLine.length; i++) {
                newLine[i] = line[i + 1];
            }
            if (hmap.containsKey(line[2])) {  //check if the name is not repeated
                Reporter.usageError(REPEAT_NAME);
            }
            if (!hmap.containsKey(line[3])) { //check if the source exist
                Reporter.usageError(UNKNOWN_COMPONENT);
            }
            processLine(newLine);


        } else if (line[0].equals("quit")) {
            System.out.println(" ?  -> quit");
            System.exit(1);
        }
    }
}
