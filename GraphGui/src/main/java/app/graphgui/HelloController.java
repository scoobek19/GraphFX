package app.graphgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HelloController {
    @FXML
    private TextField gSizeText, gWeightText, fileSaveField;
    @FXML
    private AnchorPane gPane;
    @FXML
    private CheckBox spoj;

    private Graph g1;
    private double cordY1, cordX1;

    private ArrayList<Double> cordX = new ArrayList<>();
    private ArrayList<Double> cordY = new ArrayList<>();

    private int Points = 0;
    private int VertNumb;
    private double diameter;
    private double maxi;
    private boolean coh = true;

    Aler aler1 = new Aler(Alert.AlertType.ERROR);
    Aler aler2 = new Aler(Alert.AlertType.INFORMATION);
    private static List<Integer> selectedCircles = new ArrayList<>();
    private static List<Node> nodes = new ArrayList<>();
    private static List<Node> pathNodes = new ArrayList<>();

    public void coherButton(ActionEvent event){
        coh = spoj.isSelected() ? true : false ;
    }

    public void generate(ActionEvent event) {
        gPane.getChildren().clear();
        String[] gSize, gWeight;
        int s1, s2, w1, w2;
        try {
            gSize = gSizeText.getText().split("x");
            gWeight = gWeightText.getText().split("-");
            s1 = Integer.parseInt(gSize[0]);
            s2 = Integer.parseInt(gSize[1]);
            w1 = Integer.parseInt(gWeight[0]);
            w2 = Integer.parseInt(gWeight[1]);
            if(w1 > w2){
                System.out.println("W");

                return;
            }
            g1 = new Graph(s1, s2);
            g1.gen_graph(s1, s2, w1, w2, coh);
            drawG();
        } catch (NumberFormatException e) {
            aler1.alertText("In Ordnung!", "Wrong Argument", "Wrong format of Graph size/weight given!" );
            gSizeText.setText("");
        }
    }


    public void save(ActionEvent event){
        if (Objects.equals(fileSaveField.getText(), "")) {
            fileSaveField.setText("tajpei");
        }
        final Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = null;
        try {
            fileChooser.setTitle("Open Graph File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files",".");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName(fileSaveField.getText());
            fileChooser.getInitialFileName();
            selectedFile = fileChooser.showSaveDialog(primaryStage);
        }catch (NullPointerException e){
            System.err.println("rfewf");
        }
        try {
            Utils.GraphtoFile(g1, selectedFile);
        } catch (RuntimeException | IOException e) {
            aler1.alertText("In ordnung!", "Cannot Save graph to file", "Some error occurred");
        }
    }


    public void read(ActionEvent event) {
        try {
            g1 = new Graph();
            g1 = g1.ReadtoGraph();
            drawG();
        } catch (NullPointerException e) {
            aler1.alertText("In Ordnung!", "Cannot Read Graph", "File not chosen");
            fileSaveField.setText("");
        } catch (IOException e) {
            aler1.alertText("In Ordnung!", "Cannot Read Graph", "File does not exist/Path is incorrect");
        } catch (RuntimeException e){
            aler1.alertText("In Ordnung!", "Cannot Read Graph", "File is not readable!");
        }
    }


    public class Kolko extends Circle {
        private final int id;

        public Kolko(double v, double v1, double v2, int id) {
            super(v, v1, v2);
            this.id = id;
            this.setOnMouseClicked((mouseEvent) -> {
                if(coh == true) {
                    selectedCircles.add(id);
                    Points++;
                    if (Points == 2) {
                        try {
                            DFS d1 = new DFS();
                            d1.dij(g1, selectedCircles.get(0), selectedCircles.get(1));
                            aler2.alertText("Information", d1.toString(), "Path Value is " + d1.getPathValue());
                            dijDraw(d1.getPath());
                        } catch (IndexOutOfBoundsException en) {
                        }
                        selectedCircles.clear();
                        Points = 0;
                    }
                }
            });
        }
    }


    public void drawG() {
        gPane.getChildren().clear();
        nodes.clear();
        cordX.clear();
        cordY.clear();
        int guard = 0;
        maxi = Math.max(g1.Getr(), g1.Getc());
        System.out.println();
        diameter = gPane.getWidth() / (2 * maxi);
        cordY1 = diameter;

        for (int j = 0; j < g1.Getr(); j++) {
            cordX1 = 0 + diameter;
            for (int i = 0; i < g1.Getc(); i++) {
                Circle c = new Kolko(cordX1, cordY1, diameter / 2, i + j * g1.Getc());
                nodes.add(c);
                nodes.get(nodes.size()-1).setStyle("-fx-stroke: gray; -fx-fill: gray");
                cordX1 += diameter * 2;
                cordX.add(cordX1);
                cordY.add(cordY1);
                if ((guard + 1) % g1.Getc() != 0 && g1.graphCheckAdj(guard, guard + 1)) {

                    nodes.add(new Line(cordX1 - diameter * 2, cordY1, cordX1, cordY1));
                    nodes.get(nodes.size()-1).setStyle("-fx-stroke: gray; -fx-fill: gray; -fx-stroke-width:" + 25/maxi);
                }
                if (guard % g1.Getc() != 0 && g1.graphCheckAdj(guard, guard - 1)) {
                    nodes.add(new Line(cordX1 - diameter * 2, cordY1, cordX1 - diameter * 4, cordY1)); //lewo
                    nodes.get(nodes.size()-1).setStyle("-fx-stroke: gray; -fx-fill: gray; -fx-stroke-width:" + 25/maxi);
                }
                if (guard >= g1.Getc() && g1.graphCheckAdj(guard, guard - g1.Getc())) {
                    nodes.add(new Line(cordX1 - diameter * 2, cordY1, cordX1 - diameter * 2, cordY1 - diameter * 2)); // gora
                    nodes.get(nodes.size()-1).setStyle("-fx-stroke: gray; -fx-fill: gray; -fx-stroke-width:" + 25/maxi);
                }
                if (guard < (g1.Getr() - 1) * g1.Getc() && g1.graphCheckAdj(guard, guard + g1.Getc())) {
                    nodes.add(new Line(cordX1 - diameter * 2, cordY1, cordX1 - diameter * 2, cordY1 + diameter * 2)); //gora
                    nodes.get(nodes.size()-1).setStyle("-fx-stroke: gray; -fx-fill: gray; -fx-stroke-width: " + 25/maxi);
                }
                guard++;
            }
            cordY1 += diameter * 2;
        }
       gPane.getChildren().addAll(nodes);
    }

    public void dijDraw(ArrayList<Integer> path) {
        gPane.getChildren().removeAll(pathNodes);
        pathNodes.clear();
        int VertNumb2;
        try {
            VertNumb = path.get(path.size() - 1);
            cordX1 = cordX.get(VertNumb) - diameter * 2;
            cordY1 = cordY.get(VertNumb);
        }catch (IndexOutOfBoundsException e){

        }
        for (int i = path.size()-2; i >= 0; i--) {
            VertNumb2 = path.get(i);
            if (VertNumb2 == VertNumb + 1) {
                pathNodes.add(new Line(cordX1, cordY1, cordX1 + diameter * 2, cordY1));
            } else if (VertNumb2 == VertNumb - 1) {
                pathNodes.add(new Line(cordX1, cordY1, cordX1 - diameter * 2, cordY1));//lewo
            } else if (VertNumb2 == VertNumb - g1.Getc()) {
                pathNodes.add(new Line(cordX1, cordY1, cordX1, cordY1 - diameter * 2)); // dol
            } else if (VertNumb2 == VertNumb + g1.Getc()) {
                pathNodes.add(new Line(cordX1, cordY1, cordX1, cordY1 + diameter * 2)); //gora
            }
            pathNodes.get(pathNodes.size()-1).setStyle("-fx-stroke: red; -fx-stroke-width:" + 25/maxi);
            VertNumb = VertNumb2;
            cordX1 = cordX.get(VertNumb) - diameter * 2;
            cordY1 = cordY.get(VertNumb);
        }
        gPane.getChildren().addAll(pathNodes);
    }
}