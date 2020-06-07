package model.interpreter.experssions;

public class Minus extends BinaryExpression {
	// CTOR.
	public Minus(Expression left, Expression right) { super(left, right); }

	@Override
	public double calculate() { return left.calculate() - right.calculate(); }
}