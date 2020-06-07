package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import model.interpreter.ParserAutoPilot;
import model.interpreter.commands.DisconnectCommand;
import viewModel.ViewModel;

public class RunClientMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Flight.fxml"));
        Parent root = loader.load();
        FlightController ctrl = loader.getController();
        ViewModel viewModel = new ViewModel();
        Model model = new Model();
        model.addObserver(viewModel);
        viewModel.setModel(model);
        viewModel.addObserver(ctrl);
        ctrl.setViewModel(viewModel);
        
        primaryStage.setTitle("F l i g h t    G e a r    S i m u l a t o r");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            DisconnectCommand command = new DisconnectCommand();
            String[] disconnect = {""};
            command.doCommand(disconnect);
            ParserAutoPilot.exe.interrupt();
            model.stopAll();
            System.out.println("bye");
        });
    }

    public static void main(String[] args) { launch(args); }
}