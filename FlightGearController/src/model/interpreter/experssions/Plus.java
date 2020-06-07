package model.interpreter.experssions;

public class Plus extends BinaryExpression {
	// CTOR.
	public Plus(Expression left, Expression right) { super(left, right); }

	@Override
	public double calculate() { return left.calculate() + right.calculate(); }
}