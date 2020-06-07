package model.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexer<V> implements ILexer {
    private Scanner scan;
    private ArrayList<String[]> lines = new ArrayList<>();
    private String[] stringArray = null;

    // CTOR.
    public Lexer(String v) {
        try {
            scan = new Scanner(new BufferedReader(new FileReader(v)));
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }
    // CTOR,
    public Lexer(String[] strings) { stringArray = strings; }
    
    // CTOR.
    public Lexer(V v) { scan = new Scanner((Readable) v); }
    
    public ArrayList<String[]> lex() {
        if(stringArray!=null) {
            for (String string : stringArray) {
                lines.add(string.replaceFirst("=", " = ").replaceFirst("\t","").split("\\s+"));
            } 
        } else {
            while (scan.hasNextLine() == true) {
                lines.add(scan.nextLine().replaceFirst("=", " = ").replaceFirst("\t","").split("\\s+"));
            }
        }
        
        return lines;
    }
}