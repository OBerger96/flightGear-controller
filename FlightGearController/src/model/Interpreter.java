package model;

import model.interpreter.*;

public class Interpreter {
	// Data members.
    ParserAutoPilot parser;
    ILexer iLexer;

    // CTOR.
    public Interpreter() {
        String[] start= {
                "openDataServer 5400 10",
                "connect 127.0.0.1 5402",
                "var breaks = bind /controls/flight/speedbrake",
                "var throttle = bind /controls/engines/current-engine/throttle",
                "var heading = bind /instrumentation/heading-indicator/indicated-heading-deg",
                "var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt",
                "var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg",
                "var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg",
                "var rudder = bind /controls/flight/rudder",
                "var aileron = bind /controls/flight/aileron",
                "var elevator = bind /controls/flight/elevator",
                "var alt = bind /instrumentation/altimeter/indicated-altitude-ft",
                "var rpm = bind /engines/engine/rpm",
                "var hroute = 0",
                "var goal = 0",
                "var altr = 2000",
                "var e=0",
                "var r=0"

        };
        
        iLexer = new Lexer(start);
        parser = new ParserAutoPilot(new Parser(iLexer.lex()));
        parser.parse();
        
        ParserAutoPilot.stop = false;
        
        parser.execute();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) { e.printStackTrace(); }
        ParserAutoPilot.stop = true;
    }

    public void interpet(String[] list){
       iLexer = new Lexer(list);
       parser.add(iLexer.lex());
       parser.parse();
   }
    
   public void execute() { 
	   if(parser.i!=0) { parser.i--; }
	   
       parser.Continue();
       ParserAutoPilot.stop=false;
   }
   
   public void stop(){ parser.stop(); }
}