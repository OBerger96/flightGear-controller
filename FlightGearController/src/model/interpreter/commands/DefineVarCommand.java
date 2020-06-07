package model.interpreter.commands;

import model.interpreter.Parser;
import model.interpreter.Var;
import model.interpreter.experssions.ShuntingYardPostfix;

public class DefineVarCommand implements Command {

	@Override
	public void doCommand(String[] array) {
		Var v = new Var();
		if(array.length > 2) {
			if(array[3].equals("bind")) {
				Parser.symTbl.put(array[1],Parser.symTbl.get(array[4]));
			} else {
				StringBuilder exp = new StringBuilder();
				
				for (int i = 3; i < array.length; i++) { exp.append(array[i]); }
				
				v.setV(ShuntingYardPostfix.calc(exp.toString()));
				Parser.symTbl.put(array[1],v);
			}
		} else
			Parser.symTbl.put(array[1],new Var()); 
		}
}