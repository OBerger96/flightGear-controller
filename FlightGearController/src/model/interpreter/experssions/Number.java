package model.interpreter.experssions;

public class Number implements Expression{
	// Data member.
	private double value;
	
	// CTOR.
	public Number(double value) { this.value=value; }
	
	public void setValue(double value){ this.value=value; }

	@Override
	public double calculate() { return value; }
}