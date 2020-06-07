package model.interpreter.commands;

import model.Model;

public class AutoRouteCommand implements Command {
    @Override
    public void doCommand(String[] array) { Model.turn = false; }
}