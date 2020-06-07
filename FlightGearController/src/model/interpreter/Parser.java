package model.interpreter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import model.interpreter.commands.*;

public class Parser implements IParser {
	// Data member.
    private HashMap<String, CommandExpression> cmdTbl=new HashMap<>();
    private GenericFactory cmdFac = new GenericFactory();
    public static HashMap<String,Var> symTbl;
    public ArrayList<String[]> lines;
    public ArrayList<CommandExpression> comds;
    public static double returnval;
    public static HashMap<String, String> varLocations = new HashMap<>();
    public static ArrayList<String> vars;

    public Parser(ArrayList<String[]> lines) {
        this.comds = new ArrayList<>();
        this.lines = lines;
        symTbl = new HashMap<>();
        
        cmdFac.insertProduct("openDataServer",OpenDataServer.class);
        cmdFac.insertProduct("connect",ConnectCommand.class);
        cmdFac.insertProduct("while",LoopCommand.class);
        cmdFac.insertProduct("var",DefineVarCommand.class);
        cmdFac.insertProduct("return",ReturnCommand.class);
        cmdFac.insertProduct("=",AssignCommand.class);
        cmdFac.insertProduct("disconnect",DisconnectCommand.class);
        cmdFac.insertProduct("print",PrintCommand.class);
        cmdFac.insertProduct("sleep",SleepCommand.class);
        cmdFac.insertProduct("predicate",PredicateCommand.class);
        cmdFac.insertProduct("autoroute",AutoRouteCommand.class);
        cmdFac.insertProduct("if",IfCommand.class);
        cmdTbl.put("openDataServer", new CommandExpression(new OpenDataServer()));
        cmdTbl.put("connect",new CommandExpression(new ConnectCommand()));
        cmdTbl.put("while",new CommandExpression(new LoopCommand()));
        cmdTbl.put("var",new CommandExpression(new DefineVarCommand()));
        cmdTbl.put("return",new CommandExpression(new ReturnCommand()));
        cmdTbl.put("=",new CommandExpression(new AssignCommand()));
        cmdTbl.put("disconnect",new CommandExpression(new DisconnectCommand()));
        
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader("simulator_vars.txt")));
        } catch (FileNotFoundException e) { e.printStackTrace(); }
        
        vars = new ArrayList<>();
        
        while(s.hasNext() == true) {
            vars.add(s.nextLine());
        }
        
        for (String str : vars) {
            symTbl.put(str, new Var(str));
        }
    }

    private int findCloser(ArrayList<String[]> array) {
        Stack<String> stack=new Stack<>();
        stack.push("{");
        
        for(int i=0;i<array.size();i++) {
            if(array.get(i)[0].equals("while") || array.get(i)[0].equals("if")) {stack.push("{"); }
        
            if(array.get(i)[0].equals("}")) {
                stack.pop();
                if(stack.isEmpty()) { return i; }
            }
        }
        
        return 0;
    }

    private ArrayList<CommandExpression> parseCommands(ArrayList<String[]> array) {
        ArrayList<CommandExpression> commands=new ArrayList<>();
        
        for (int i = 0; i < array.size(); i++) {
            CommandExpression e = new CommandExpression((Command)cmdFac.getNewProduct(array.get(i)[0]));
            
            if(e.getC() != null) {
                if(array.get(i)[0].equals("while") || array.get(i)[0].equals("if") ) {
                    int index = i;
                    i += findCloser(new ArrayList<>(array.subList(i+1,array.size())))+1;
                    e.setC(this.parseCondition(new ArrayList<>(array.subList(index, i))));
                }
                
                e.setS(array.get(i));
            } else {
                e = new CommandExpression((Command)cmdFac.getNewProduct(array.get(i)[1]));
                e.setS(array.get(i));
            }
            
            commands.add(e);
        }
        
        return commands;
    }

    public void parse() { this.comds=this.parseCommands(lines); }

    public double execute() {
        for (CommandExpression e:comds) {
        	e.calculate();
        }
        
        return returnval;
    }

    private Command parseCondition(ArrayList<String[]> array) {
        ConditionCommand c = (ConditionCommand)cmdFac.getNewProduct(array.get(0)[0]);
        int i = 0;
        ArrayList<CommandExpression> tmp = new ArrayList<>();
        CommandExpression cmdtmp = new CommandExpression((Command)cmdFac.getNewProduct("predicate"));
        
        cmdtmp.setS(array.get(0));
        tmp.add(cmdtmp);
        c.setCommands(tmp);
        c.getCommands().addAll(1,this.parseCommands(new ArrayList<>(array.subList(i + 1, array.size()))));
 
        return c;

    }
}