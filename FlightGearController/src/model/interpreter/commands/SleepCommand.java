package model.interpreter.commands;

import model.interpreter.experssions.ShuntingYardPostfix;

public class SleepCommand implements Command {
    @Override
    public void doCommand(String[] array) {
        try {
            Thread.sleep((long)ShuntingYardPostfix.calc(array[1]));
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}