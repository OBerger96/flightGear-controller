package model.interpreter.commands;

import model.interpreter.experssions.ExpressionBulider;

public class PredicateCommand implements Command {
	// Data member.
	double check;
	
	// Get & Set check.
	public double getBool() { return check; }
    public void setBool(double check) { this.check = check; }

    @Override
    public void doCommand(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1;i < (array.length - 1); i++){ stringBuilder.append(array[i]); }
        
        check= ExpressionBulider.calc(stringBuilder.toString());
    }
}
