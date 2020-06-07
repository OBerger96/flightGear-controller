package model.interpreter.commands;

import model.interpreter.Parser;
import model.interpreter.experssions.ShuntingYardPostfix;

public class AssignCommand implements Command {
    @Override
    public void doCommand(String[] array) {
        if (array[2].equals("bind")) {
            if(Parser.symTbl.get(array[0]).getV()!=Parser.symTbl.get(array[3]).getV()) { 
            	Parser.symTbl.get(array[0]).setV(Parser.symTbl.get(array[3]).getV()); 
            }
            
            Parser.symTbl.get(array[3]).addObserver(Parser.symTbl.get(array[0]));
            Parser.symTbl.get(array[0]).addObserver(Parser.symTbl.get(array[3]));
        } else {
            StringBuilder exp = new StringBuilder();
            
            for (int i = 2; i < array.length; i++) { exp.append(array[i]); }
            
            double tmp = ShuntingYardPostfix.calc(exp.toString());
            
            if(Parser.symTbl.get(array[0]).getLoc()!=null) {
                ConnectCommand.out.println("set " + Parser.symTbl.get(array[0]).getLoc() + " " + tmp);
                ConnectCommand.out.flush();
                System.out.println("set " + Parser.symTbl.get(array[0]).getLoc() + " " + tmp);
            } else { Parser.symTbl.get(array[0]).setV(tmp); }
        }
    }
}