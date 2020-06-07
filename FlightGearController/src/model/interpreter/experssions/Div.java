package model.interpreter.experssions;

public class Div extends BinaryExpression {
	// CTOR.
	public Div(Expression left, Expression right) { super(left, right); }

	@Override
	public double calculate() { return left.calculate() / right.calculate(); }
}