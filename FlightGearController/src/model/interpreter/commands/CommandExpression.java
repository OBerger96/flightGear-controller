package model.interpreter.commands;

import model.interpreter.experssions.Expression;

public class CommandExpression implements Expression {
    // Data members.
	private Command command;
    private String[] strings;

    // CTOR.
    public CommandExpression(Command c) { this.command = c; }
    
    // Get & Set command.
    public Command getC() { return command; }
    public void setC(Command c) { this.command = c; }

    public String[] getS() { return strings; }

    public void setS(String[] s) { this.strings = s; }

    @Override
    public double calculate() {
        command.doCommand(strings);
        return 0;
    }
}