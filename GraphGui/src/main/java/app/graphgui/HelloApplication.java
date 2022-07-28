package app.graphgui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Graph GUI");
        stage.setScene(scene);
        stage.show();

        Graph g1 = HelloController.g1;
        double[] cordY = HelloController.cordY;
        double[] cordX = HelloController.cordX;
        double diameter = HelloController.diameter;
        GraphicsContext gc = HelloController.gc;

        EventHandler<MouseEvent> ev = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double mouseX1 = 0;
                double mouseY1 = 0;
                int endV = 0, startV = 0;
                if (HelloController.g1 != null) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        mouseX1 = event.getX();
                        mouseY1 = event.getY();
                        endV = 0;

                        for (int i = 0; i < HelloController.g1.Getr() * g1.Getc(); i++) {
                            if ((mouseX1 >= cordX[i] && mouseX1 <= cordX[i] + diameter) && (mouseY1 >= cordY[i] && mouseY1 <= cordY[i] + diameter * 2)) {
                                startV = i;
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        double mouseX2 = event.getX();
                        double mouseY2 = event.getY();
                        endV = 0;
                        for (int i = 0; i < HelloController.g1.Getr() * g1.Getc(); i++) {
                            if ((mouseX2 >= cordX[i] && mouseX2 <= cordX[i] + diameter) && (mouseY2 >= cordY[i] && mouseY2 <= cordY[i] + diameter * 2)) {
                                endV = i;
                            }
                            if(endV != 0){
                                DFS d1 = new DFS();
                                d1.dij(g1, startV, endV);
                                ArrayList <Integer> path = d1.path;
                                drawPath(path);
                            }
                        }
                    }
                }
            }
            public void drawPath(ArrayList <Integer> path) {
                gc.setStroke(Color.BLACK);
                int end = path.get(0);
                for (int i = 1; i < path.size(); i++) {
                    int guard = path.get(i);
                    if (guard == end + 1) {
                        gc.strokeLine(cordX[guard] + diameter, cordY[guard] + diameter / 2, cordX[guard] + diameter * 2, cordY[guard] + diameter / 2); //prawo
                    }
                    if (guard == end - 1) {
                        gc.strokeLine(cordX[guard], cordY[guard] + diameter / 2, cordX[guard] - diameter, cordY[guard] + diameter / 2); //lewo
                    }
                    if (guard == end + g1.Getc()) {
                        gc.strokeLine(cordX[guard] + diameter / 2, cordY[guard], cordX[guard] + diameter / 2, cordY[guard] - diameter); // dol
                    }
                    if (guard == end - g1.Getc()) {
                        gc.strokeLine(cordX[guard] + diameter / 2, cordY[guard] + diameter, cordX[guard] + diameter / 2, cordY[guard] + diameter * 2); //gora
                    }
                    end = guard;
                }
            }
    };
        if(HelloController.s == 1)
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED,ev);
}

    public static void main(String[] args) {
        launch();
    }
}