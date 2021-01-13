package sample;

import com.sun.xml.internal.ws.util.StringUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.logging.Logger;
import java.io.*;
import java.util.logging.Level;
import javafx.scene.input.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MenuBar bar = new MenuBar();
        TextArea txt = new TextArea();
        SeparatorMenuItem sep1 = new SeparatorMenuItem();
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        SeparatorMenuItem sep3 = new SeparatorMenuItem();

        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        // File Menu
        MenuItem nnew = new MenuItem("New");
        nnew.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        nnew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.clear();
                primaryStage.setTitle("Untitled");
            }
        });

        MenuItem open = new MenuItem("Open");
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser dialog = new FileChooser();
                dialog.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt*"));
                File file = dialog.showOpenDialog(primaryStage);

                try (BufferedReader reader = new BufferedReader(new FileReader(new File(file.toURI())))) {
                    String line;
                    primaryStage.setTitle(file.getName());
                    while ((line = reader.readLine()) != null) {
                        txt.appendText(line+"\n");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem save = new MenuItem("Save");
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(primaryStage);

                if(file != null){
                    SaveFile(txt.getText(), file);
                    primaryStage.setTitle(file.getName());
                }
            }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setAccelerator(KeyCombination.keyCombination("Ctrl+w"));
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Notepad");
                alert.setContentText("Are you sure you want to exit ?");
                //alert.setResult(null);
               String s = new String();
               s += alert.showAndWait().get().getText();
               if(s.equals("OK"))
                {
                    Platform.exit();
                }
               else
               {
                   alert.close();
               }
            }
        });

       //Edit Menu
        MenuItem undo = new MenuItem("Undo");
        undo.setAccelerator(KeyCombination.keyCombination("Ctrl+z"));
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.undo();
            }
        });

        MenuItem cut = new MenuItem("Cut");
        cut.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.cut();
            }
        });

        MenuItem paste = new MenuItem("Paste");
        paste.setAccelerator(KeyCombination.keyCombination("Ctrl+v"));
        paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.paste();
            }
        });

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.deleteText(txt.getSelection());
            }
        });

        MenuItem selectAll = new MenuItem("Select All");
        selectAll.setAccelerator(KeyCombination.keyCombination("Ctrl+a"));
        selectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    txt.selectAll();
            }
        });
        //About Menu
        MenuItem about = new MenuItem("About Notepad");
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notepad");
                alert.setContentText("Author: Aya Abdulsamie"+"\n"+"Description: Mini Notepad using javaFx");
                alert.showAndWait();
            }
        });

        file.getItems().addAll(nnew,open,save,sep1,exit);
        edit.getItems().addAll(undo,sep2,cut,paste,delete,sep3,selectAll);
        help.getItems().addAll(about);

        bar.getMenus().addAll(file,edit,help);
        BorderPane pane = new BorderPane();
        pane.setTop(bar);
        pane.setCenter(txt);

        Scene scene = new Scene(pane, 700, 700);
        primaryStage.setTitle("Untitled");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
}
