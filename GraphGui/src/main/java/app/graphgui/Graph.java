package app.graphgui;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {
    private int gsize = 0;
    private int r;
    private int c;
    private Vert[] wertex;
    private Aler aler1 = new Aler(Alert.AlertType.WARNING);

    public Graph() {
    }

    public int Getr() {
        return r;
    }

    public int Getc() {
        return c;
    }

    public int Getgsize() {
        return gsize;
    }

    public Vert getWertex(int i) {
        return wertex[i];
    }

    public boolean graphCheckAdj(int wert, int adj){
        return wertex[wert].CheckIfAdj(adj);
    }

    public Graph(int r, int c) {
        gsize = r * c;
        this.wertex = new Vert[gsize];
        for (int i = 0; i < gsize; i++) {
            this.wertex[i] = new Vert(i);
        }
        this.r = r;
        this.c = c;
    }

    public Graph gen_graph(int r1, int c1, double w_min, double w_max, boolean coher) {
        System.out.println(coher);
        double wertWeig, cohernit;
        int i, j, count = 0;
            for (i = 0; i < r1; i++) {
                for (j = 0; j < c1; j++) {
                    if ((count + 1) % c1 != 0) {
                        wertWeig = Math.round(ThreadLocalRandom.current().nextDouble(w_min, w_max) * 10000.0) / 10000.0;
                        cohernit = wertWeig % 10;
                        if(coher == true || (coher == false && cohernit <= 5))
                            this.wertex[count].addSas(count + 1, wertWeig);
                    }
                    if (count % c1 != 0)                            //wypelnianie krawedzi zewn grafu
                    {
                        wertWeig = Math.round(ThreadLocalRandom.current().nextDouble(w_min, w_max) * 10000.0) / 10000.0;
                        cohernit = wertWeig % 10;
                        if(coher == true || (coher == false && cohernit <= 5))
                            this.wertex[count].addSas(count - 1, wertWeig);
                    }
                    if (count >= c1)                            //kraw zew
                    {
                        wertWeig = Math.round(ThreadLocalRandom.current().nextDouble(w_min, w_max) * 10000.0) / 10000.0;
                        cohernit = wertWeig % 10;
                        if(coher == true || (coher == false && cohernit <= 5))
                            this.wertex[count].addSas(count - c1, wertWeig);
                    }
                    if (count < (r1 - 1) * c1) {
                        wertWeig = Math.round(ThreadLocalRandom.current().nextDouble(w_min, w_max) * 10000.0) / 10000.0;
                        cohernit = wertWeig % 10;
                        if(coher == true || (coher == false && cohernit <= 5))
                            this.wertex[count].addSas(count + c1, wertWeig);
                    }
                    count++;
            }
        }
        return this;
    }

    public Graph ReadtoGraph() throws IOException{
        final Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Graph File");
        File fil = null;
        try{
            fil = fileChooser.showOpenDialog(primaryStage);}
        catch (NullPointerException e){
            aler1.alertText("In ordnung!", "Cannot read Graph", "File was not selected!");
        }

        if (fil == null) {}
            Scanner s = new Scanner(fil);
            String line;
            line = s.nextLine();
            String[] words = line.split("\\s+");
            int ro, co;
            if (words.length == 2) {
                try {
                    ro = Integer.parseInt(words[0]);
                    co = Integer.parseInt(words[1]);
                    if (ro < 0 || co < 0) {
                        System.err.println("Liczby nie moga byc ujemne!!!");
                        System.exit(3);
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("zly format linii!!");
                }
            } else {
                throw new IOException("Linia nie zawiera 2 pól!");
            }
            Graph g1 = new Graph(ro, co);

            int berta, guard = 0;
            double berta_weight;
            int size = ro * co;
            while (s.hasNextLine()) {
                line = s.nextLine();
                if (line.isBlank()) {
                    guard++;
                    continue;
                }
                String[] words1 = line.split("\\s+:\\s+|:\\s+|\\s+:|\\s+");

                for (int j = 1; j < words1.length; j += 2) {
                    try {
                        berta = Integer.parseInt(words1[j]);
                        berta_weight = Double.parseDouble(words1[j + 1]);
                        if (berta > size - 1 || berta < 0) {
                            System.err.println("Zla wartosc krawedzi!");
                            System.exit(3);
                        }

                        if (berta_weight < 0) {
                            System.err.println("Waga krawedzi mniejsza niz 0!!");
                            System.exit(3);
                        }
                        g1.wertex[guard].addSas(berta, berta_weight);
                    } catch (NumberFormatException e) {
                        throw new IOException("Zly format wierzcholka lub wagi!");
                    }
                }
                guard++;
            }
            return g1;
        }
    }
