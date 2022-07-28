package app.graphgui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloController {
    @FXML
    private TextField gSizeText, gWeightText, fileSaveField;
    @FXML
    private Canvas gCanva;
    @FXML
    private AnchorPane gPane;

    Alert a;
    protected static Graph g1;
    protected static GraphicsContext gc;
    Aler al1 = new Aler();

    protected static int s;
    protected static double diameter;
    protected static double[] cordX;
    protected static double[] cordY;
    double cordY1, cordX1;
    protected static int start, end;

    public void clear(GraphicsContext gra1, Canvas canv) {
        gra1.clearRect(0, 0, canv.getWidth(), canv.getHeight());
    }

    public void generate(ActionEvent event) {
        String[] gSize, gWeight;
        int s1, s2, w1, w2;
        try {
            gSize = gSizeText.getText().split("x");
            gWeight = gWeightText.getText().split("-");
            s1 = Integer.parseInt(gSize[0]);
            s2 = Integer.parseInt(gSize[1]);
            w1 = Integer.parseInt(gWeight[0]);
            w2 = Integer.parseInt(gWeight[1]);

            g1 = new Graph(s1, s2);
            g1.gen_graph(s1, s2, w1, w2, 1);
            drawG();
        } catch (NumberFormatException e) {
            al1.Error("In Ordnung!", "Wrong Argument", "Wrong format of Graph size given!", gSizeText);
        }
    }


    public void save(ActionEvent event) throws IOException {
        if (g1 == null) {
            Aler.Error("In Ordnung!", "Cannot save file!", "Graph is empty or not loaded!", gSizeText);
        }
        if (fileSaveField.getText() == "") {
            fileSaveField.setText("tajpei");
        }
        Utils.GraphtoFile(g1, fileSaveField.getText());
    }


    public void read(ActionEvent event) {
        try {
            g1 = new Graph();
            g1 = g1.ReadtoGraph();
            Utils.GraphOut(g1);
            drawG();
        } catch (IOException e) {
            Aler.Error("In Ordnung!", "Cannot Read Graph", "File does not exist/Path is incorrect", gSizeText);
        }
    }


    public static List<Integer> selectedCircles = new ArrayList<>();

    public class Kolko extends Circle {
        private final int id;

        public Kolko(double v, double v1, double v2, int id) {
            super(v, v1, v2);
            this.id = id;
            this.setOnMouseClicked((mouseEvent) -> {
                selectedCircles.add(id);
                System.err.println(id);
            });
        }

        public int getKolkoId() {
            return id;
        }


    }


    public void drawG() {


        int guard = 0;

        //gPane.setStyle("-fx-background-color: #52a1de");

        List<Node> nodes = new ArrayList<>();


//        gc = gCanva.getGraphicsContext2D();
        //TODO: zmiana tła
        //gCanva.setStyle("-fx-background-color: pink");
        cordX = new double[g1.Getr() * g1.Getc()];
        cordY = new double[g1.Getr() * g1.Getc()];
//        gc.setFill(Color.BLACK);

//        clear(gc, gCanva);
//        gc.setStroke(Color.BLUEVIOLET);
        double maxi = Math.max(g1.Getr(), g1.Getc());
        diameter = gPane.getWidth() /  (2 * maxi);
        cordY1 = diameter;

        for (int j = 0; j < g1.Getr(); j++) {
            cordX1 = 0 + diameter ;
            for (int i = 0; i < g1.Getc(); i++) {
//                gc.setFill(Color.TURQUOISE);

//                gc.fillOval(cordX1, cordY1, diameter, diameter);
                Circle c = new Kolko(cordX1, cordY1, diameter/2, i + j * g1.Getc());
                nodes.add(c);

                cordX[guard] = cordX1;
                cordY[guard] = cordY1;
                cordX1 += diameter * 2 ;
                guard++;
            }
            cordY1 += diameter * 2 ;
        }
        guard = 0;
//        gc.setLineWidth(4);
        for (int i = 0; i < g1.Getr(); i++) {
            for (int j = 0; j < g1.Getc(); j++) {
                if ((guard + 1) % g1.Getc() != 0) {
//                    gc.strokeLine(cordX[guard] + diameter, cordY[guard] + diameter / 2, cordX[guard] + diameter * 2, cordY[guard] + diameter / 2); //prawo
                    nodes.add(new Line(cordX[guard], cordY[guard], cordX[guard] + diameter * 2, cordY[guard]));

                }
                if (guard % g1.Getc() != 0) {
                    nodes.add(new Line(cordX[guard], cordY[guard] + diameter / 2, cordX[guard] - diameter, cordY[guard] + diameter / 2)); //lewo
                }
                if (guard >= g1.Getc()) {
                    nodes.add(new Line(cordX[guard] + diameter / 2, cordY[guard], cordX[guard] + diameter / 2, cordY[guard] - diameter)); // dol
                }
                if (guard < (g1.Getr() - 1) * g1.Getc()) {
                    nodes.add(new Line(cordX[guard] + diameter / 2, cordY[guard] + diameter, cordX[guard] + diameter / 2, cordY[guard] + diameter * 2)); //gora
                }
                guard++;
            }
        }
        gPane.getChildren().addAll(nodes);

    }

    public void DijWork(int v1, int v2) {
        DFS d1 = new DFS();
        d1.dij(g1, v1, v2);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.RED);
        for (int i = 0; i < d1.path.size(); i++) {
            gc.fillOval(cordX[d1.path.get(i)], cordY[d1.path.get(i)], diameter, diameter);
        }
        for (int i = 0; i < d1.path.size(); i++) {
            gc.fillOval(cordX[d1.path.get(i)], cordY[d1.path.get(i)], diameter, diameter);
        }
        for (int i = 0; i < d1.path.size() - 1; i++) {
            if (d1.path.get(i + 1) == d1.path.get(i) - 1) {
                gc.strokeLine(cordX[d1.path.get(i)], cordY[d1.path.get(i)] + diameter / 2, cordX[d1.path.get(i)] - diameter, cordY[d1.path.get(i)] + diameter / 2);
            }
            if (d1.path.get(i + 1) == d1.path.get(i) + 1) {
                gc.strokeLine(cordX[d1.path.get(i)] + diameter, cordY[d1.path.get(i)] + diameter / 2, cordX[d1.path.get(i)] + diameter * 2, cordY[d1.path.get(i)] + diameter / 2);
            }
            if (d1.path.get(i + 1) == d1.path.get(i) - g1.Getc()) {
                gc.strokeLine(cordX[d1.path.get(i)] + diameter / 2, cordY[d1.path.get(i)], cordX[d1.path.get(i)] + diameter / 2, cordY[d1.path.get(i)] - diameter);
            }
            if (d1.path.get(i + 1) == d1.path.get(i) + g1.Getc()) {
                gc.strokeLine(cordX[d1.path.get(i)] + diameter / 2, cordY[d1.path.get(i)] + diameter, cordX[d1.path.get(i)] + diameter / 2, cordY[d1.path.get(i)] + diameter * 2);
            }
        }
    }

   /* public void rozpoczecieRys(MouseEvent event) {

        double mouseX1 = event.getX();
        double mouseY1 = event.getY();
        Points++;
        EventHandler<MouseEvent> ev = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        };

            for (int i = 0; i < g1.Getr() * g1.Getc(); i++) {
                if ((mouseX1 >= cordX[i] && mouseX1 <= cordX[i] + diameter) && (mouseY1 >= cordY[i] && mouseY1 <= cordY[i] + diameter * 2)) {
                    if (Points % 2 == 1) {
                        start = i;
                        gc.setStroke(Color.BLACK);
                        gc.strokeOval(cordX[i], cordY[i], diameter, diameter);
                    } else if (Points % 2 == 0) {
                        end = i;
                        gc.setStroke(Color.BLACK);
                        gc.strokeOval(cordX[i], cordY[i], diameter, diameter);
                        DijWork(start, end);
                    }
                }
            }
     }
*/

  /*  public void dijkBut(ActionEvent actionEvent) {

        if (g1 != null) {
            int skad = 0;
            int dokad = 8;
            try {
                drawG();
                for (int i = 0; i < g1.Getr() * g1.Getc(); i++)
                {
                    canvas.setStroke(Color.BLACK);
                    canvas.strokeOval(cordX[start], cordY[start], diameter, diameter);
                    canvas.setStroke(Color.BLACK);
                    canvas.strokeOval(cordX[end], cordY[end], diameter, diameter);
                    DijWork(skad, dokad);
                }
            } catch (Exception e) {
                System.out.println("aaaa");
            }
        }
    }

   */
}