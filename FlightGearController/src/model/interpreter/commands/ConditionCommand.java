package model.interpreter.commands;

import java.util.ArrayList;

public class ConditionCommand implements Command {
	// Data member.
	protected ArrayList<CommandExpression> commands;
	
	// Get & Set commands;
	public ArrayList<CommandExpression> getCommands() { return commands; }
	public void setCommands(ArrayList<CommandExpression> commands) { this.commands = commands; }

	@Override
	public void doCommand(String[] array) { }
}