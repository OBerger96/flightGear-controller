package model.interpreter.experssions;

public class Mul extends BinaryExpression {
	// CTOR.
	public Mul(Expression left, Expression right) { super(left, right); }

	@Override
	public double calculate() { return left.calculate() * right.calculate(); }
}