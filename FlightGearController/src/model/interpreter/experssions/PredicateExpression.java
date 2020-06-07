package model.interpreter.experssions;

import java.util.function.Predicate;

public class PredicateExpression extends BinaryExpression {
    // Data members.
	String cond;
    Predicate<Expression> par;
    
    // CTOR.
    public PredicateExpression(Expression left, Expression right,String cond) {
        super(left, right);
        this.cond = cond;
    }

    @Override
    public double calculate() {
        switch (cond) {
            case "<":
                par = new Predicate<Expression>() {
                    @Override
                    public boolean test(Expression expression) {
                       return (expression.calculate() < right.calculate());
                    }
                };
                break;
            case ">":
                par = new Predicate<Expression>() {
                    @Override
                    public boolean test(Expression expression) {
                        return (expression.calculate() > right.calculate());
                    }
                };
                break;
            case "<=":
                par = new Predicate<Expression>() {
                    @Override
                    public boolean test(Expression expression) {
                        return (expression.calculate() <= right.calculate());
                    }
                };
                break;
            case ">=":
                par = new Predicate<Expression>() {
                    @Override
                    public boolean test(Expression expression) {
                        return (expression.calculate() >= right.calculate());
                    }
                };
                break;
            case "==":
                par = new Predicate<Expression>() {
                    @Override
                    public boolean test(Expression expression) {
                        return (expression.calculate() == right.calculate());
                    }
                };
                break;
            case "!=":
                par = new Predicate<Expression>() {
                    @Override
                    public boolean test(Expression expression) {
                        return (expression.calculate() != right.calculate());
                    }
                };
                break;
            default:
                break;
        }
        
        if(par.test(left)) { return 1; }
        
        return 0;
    }
}