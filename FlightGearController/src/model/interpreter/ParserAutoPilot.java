package model.interpreter;


import java.util.ArrayList;

public class ParserAutoPilot {
    // Data member.
	Parser parser;
    public static volatile boolean stop = true;
    public static volatile boolean close = false;
    public static Thread  exe;
    public int i = 0;
    
    // CTOR.
    public ParserAutoPilot(Parser parser) { this.parser = parser; }
    
    public void parse() {
        parser.parse();
        i = 0;
    }

    public void execute() {
        exe = new Thread(() -> {
            while(!close) {
                while ((!stop) && (i < parser.comds.size())) {
                    parser.comds.get(i).calculate();
                    i++;
                }
            }
        });

        exe.start();
    }

    public void add(ArrayList<String[]> lines) {
        parser.lines.clear();
        parser.lines.addAll(lines);
        
        Parser.symTbl.put("stop",new Var(1));
        
        for (String[] strings : parser.lines) {
            if (strings[0].equals("while")) {
                StringBuilder tmp = new StringBuilder(strings[strings.length - 2]);
                tmp.append("&&stop!=0");
                strings[strings.length - 2] = tmp.toString();
            }
        }
    }
    
    public void stop() {
        Var v = Parser.symTbl.get("stop");
        if(v != null) { v.setV(0); }
        
        ParserAutoPilot.stop = true;
    }
    
    public void Continue() { Parser.symTbl.get("stop").setV(1); }
}