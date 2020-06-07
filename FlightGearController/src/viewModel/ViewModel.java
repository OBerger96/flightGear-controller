package viewModel;

import javafx.beans.property.*;
import model.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ViewModel extends Observable implements Observer {
    // Data members.
	public DoubleProperty throttle;
    public DoubleProperty rudder;
    public DoubleProperty aileron;
    public DoubleProperty elevator;
    public DoubleProperty airplaneX;
    public DoubleProperty airplaneY;
    public DoubleProperty startX;
    public DoubleProperty startY;
    public DoubleProperty offset;
    public DoubleProperty heading;
    public DoubleProperty markSceneX;
    public DoubleProperty markSceneY;
    
    public StringProperty script;
    public StringProperty ip;
    public StringProperty port;
    
    public BooleanProperty path;
    
    private int data[][];
    
    private Model model;
 
    // CTOR.
    public ViewModel() {
        throttle = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        airplaneX = new SimpleDoubleProperty();
        airplaneY = new SimpleDoubleProperty();
        startX = new SimpleDoubleProperty();
        startY = new SimpleDoubleProperty();
        offset = new SimpleDoubleProperty();
        heading = new SimpleDoubleProperty();
        markSceneX = new SimpleDoubleProperty();
        markSceneY = new SimpleDoubleProperty();
        script = new SimpleStringProperty();
        ip = new SimpleStringProperty();
        port = new SimpleStringProperty();
        path = new SimpleBooleanProperty();
    }
    
    // Setters.
    public void setData(int[][] data) {
        this.data = data;
        model.GetPlane(startX.getValue(),startY.doubleValue(),offset.getValue());
    }
    
    public void setModel(Model model) { this.model=model; }

    public void setThrottle() {
        String[] data = { "set /controls/engines/current-engine/throttle "+throttle.getValue() };
        model.send(data);
    }

    public void setRudder() {
        String[] data = { "set /controls/flight/rudder "+rudder.getValue() };
        model.send(data);
    }

    public void setJoystick() {
        String[] data = {
                "set /controls/flight/aileron " + aileron.getValue(),
                "set /controls/flight/elevator " + elevator.getValue(),
        };
        model.send(data);
    }
    
    // Calls the Models to connect to the manual controller.
    public void connect() { model.connectManual(ip.getValue(),Integer.parseInt(port.getValue())); }

    // Parsers the received script using the Model's interpreter.
    public void parse() {
        Scanner scanner = new Scanner(script.getValue());
        ArrayList<String> list = new ArrayList<>();
        
        while(scanner.hasNextLine() == true)
        {
            list.add(scanner.nextLine());
        }
        
        String[] tmp = new String[list.size()];
        
        for(int i = 0; i < list.size(); i++)
        {
            tmp[i] = list.get(i);
        }
        
        model.parse(tmp);
        
        scanner.close();
    }

    // Executes the received script using the Model's interpreter. 
    public void execute() { model.execute(); }

    // Calls the Models to stop the autopilot controller.
    public void stopAutoPilot(){ model.stopAutoPilot(); }

    // Calls the Models to find the shortest-path from the plane to its destination.
    public void findPath(double h,double w) {
    	// Checks for if it's the first time, then needed to connect.
    	if (!path.getValue()) { model.connectPath(ip.getValue(), Integer.parseInt(port.getValue())); }
    	
        model.findPath((int)(airplaneY.getValue() / (-1)),
        			   (int)(airplaneX.getValue() + (15)), 
        			   Math.abs((int)(markSceneY.getValue() / h)), 
        			   Math.abs((int)(markSceneX.getValue() / w)), 
        			   data);
    }

    // Update.
    @Override
    public void update(Observable o, Object arg) {
        if(o == model)
        {
            String[] tmp = (String[])arg;
            if(tmp[0].equals("plane")) {
                double x = Double.parseDouble(tmp[1]);
                double y = Double.parseDouble(tmp[2]);
                x = (x - startX.getValue() + offset.getValue());
                x /= offset.getValue();
                y = (y - startY.getValue() + offset.getValue()) / offset.getValue();
                airplaneX.setValue(x);
                airplaneY.setValue(y);
                heading.setValue(Double.parseDouble(tmp[3]));
                setChanged();
                notifyObservers();
            }
            else if(tmp[0].equals("path"))
            {
                setChanged();
                notifyObservers(tmp);
            }
        }
    }
}