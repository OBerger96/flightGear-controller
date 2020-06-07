package model.interpreter.commands;

public class IfCommand extends ConditionCommand {

    @Override
    public void doCommand(String[] array) {
        PredicateCommand tmp = (PredicateCommand)commands.get(0).getC();
        commands.get(0).calculate();

        if(tmp.getBool() != 0) {
            for (int i = 1; i < commands.size(); i++) { commands.get(i).calculate(); }
            
            commands.get(0).calculate();
        }
    }
}