package model.interpreter;
import java.util.ArrayList;

public interface ILexer<V> {
	public ArrayList<String[]> lex();
}