package app.graphgui;

import java.io.*;
import java.util.Scanner;

public class Graph {
    private int gsize = 0;
    private int r;
    private int c;
    private Vert[] wertex;

    public int Getr(){
        return r;}

    public int Getc(){
        return c;
    }

    public int Getgsize(){
        return gsize;
    }

    public Vert getWertex(int i) {
        return wertex[i];
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
    public Graph() {};
    public Graph gen_graph(int r1, int c1, double w_min, double w_max, int coher) {
        java.util.Random ran = new java.util.Random();
        int i, j, count = 0;

        for (i = 0; i < r1; i++) {
            for (j = 0; j < c1; j++) {
                if (i == 0 && j == 0) {                                               //wypelnianie rogow
                    this.wertex[count].addSas(count + c1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count + 1, w_min + ran.nextDouble() * (w_max - w_min));
                } else if (j == 0 && i < r1 - 1 && i != 0)                            //wypelnianie krawedzi zewn grafu
                {
                    this.wertex[count].addSas(count + c1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count + 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - c1, w_min + ran.nextDouble() * (w_max - w_min));
                } else if (i == 0 && j != 0 && j < c1 - 1)                            //kraw zew
                {
                    this.wertex[count].addSas(count + 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count + c1, w_min + ran.nextDouble() * (w_max - w_min));
                } else if (i == 0 && j == c1 - 1) {                                   //rog
                    this.wertex[count].addSas(count + c1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - 1, w_min + ran.nextDouble() * (w_max - w_min));
                } else if (i == r1 - 1 && j == 0)                                     //rog
                {
                    this.wertex[count].addSas(count + 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - c1, w_min + ran.nextDouble() * (w_max - w_min));
                } else if (i == r1 - 1 && j == c1 - 1)                                //rog
                {
                    this.wertex[count].addSas(count - 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - c1, w_min + ran.nextDouble() * (w_max - w_min));
                } else if (j == c1 - 1 && i < r1 - 1 && i != 0)                       //kraw zew
                {
                    this.wertex[count].addSas(count - 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - c1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count + c1, w_min + ran.nextDouble() * (w_max - w_min));
                } else if (i == r1 - 1 && i < c1 - 1 && j != 0)                       //kraw zew
                {
                    this.wertex[count].addSas(count - 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - c1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count + 1, w_min + ran.nextDouble() * (w_max - w_min));

                } else                                            //srodek grafu
                {
                    this.wertex[count].addSas(count + c1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count + 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - 1, w_min + ran.nextDouble() * (w_max - w_min));
                    this.wertex[count].addSas(count - c1, w_min + ran.nextDouble() * (w_max - w_min));
                }
                count++;
            }
        }
        return this;
    }

    public Graph ReadtoGraph(String path) throws IOException {
        File fil = new File(path);
        if(fil == null){
            System.out.println("File is missing!");
            System.exit(1);
        }
        Scanner s = new Scanner(fil);
        String line;
        line = s.nextLine();
        String[] words = line.split("\\s+");
        int ro, co;
        if (words.length == 2) {
            try {
                ro = Integer.parseInt(words[0]);
                co = Integer.parseInt(words[1]);
                if(ro <0 || co < 0)
                {
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
            while(s.hasNextLine()) {
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

