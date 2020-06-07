package model.interpreter.commands;

import model.interpreter.Parser;
import model.interpreter.experssions.ShuntingYardPostfix;

public class ReturnCommand implements Command {

    @Override
    public void doCommand(String[] array) {

        StringBuilder exp = new StringBuilder();
        for (int i = 1; i < array.length; i++) { exp.append(array[i]); }
        
        Parser.returnval = ShuntingYardPostfix.calc(exp.toString());
    }
}