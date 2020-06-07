package model.interpreter.experssions;

public abstract class BinaryExpression implements Expression {
	// Data member.
	protected Expression left,right;
	
	// CTOR.
	public BinaryExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
}