package model.interpreter;

import java.util.ArrayList;
import java.util.Scanner;

public class LexerSimulator<V> implements ILexer {
	// Data members.
	private Scanner scan;
	private ArrayList<String[]> lines = new ArrayList<>();
	
	// CTOR.
	public LexerSimulator(String v) { scan = new Scanner(v); }
	
	// CTOR.
	public LexerSimulator(V v) { scan = new Scanner((Readable) v); }
	
	public ArrayList<String[]> lex() {
		while (scan.hasNextLine() == true) { lines.add(scan.nextLine().split(" ")); }
		
		return lines;
	}
}