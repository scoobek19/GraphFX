package app.graphgui;

import java.io.*;

public class Utils {
    public static void GraphtoFile(Graph g1, File selectedFile) throws IOException {


        FileWriter outer = new FileWriter(selectedFile);
        outer.write(g1.Getr() + " " + g1.Getc() + "\n");

        GraphOut(g1);
        for (int j = 0; j < g1.Getgsize(); j++) {
            for (int i = 0; i < g1.getWertex(j).getN(); i++) {
                try {
                    outer.write("\t" + g1.getWertex(j).Getsas(i) + " :" + Math.round(g1.getWertex(j).Getsas_w(i) * 10000.0) / 10000.0 + "\t");
                }
                catch( IOException e){
                    System.err.println("Blad w zapisie!!");
                    System.exit(4);
                }
            }
            outer.write("\n");
        }
        outer.close();
     //   System.out.println("Pomyslnie zapisano graf do pliku: " + path);
     //   System.exit(0);
    }


    public static void GraphOut(Graph g1) {
        System.out.println(g1.Getr() + " " + g1.Getc());

        for (int j = 0; j < g1.Getgsize(); j++) {
            for (int i = 0; i < g1.getWertex(j).getN(); i++) {
                System.out.print("\t"  + g1.getWertex(j).Getsas(i) + " :" + Math.round(g1.getWertex(j).Getsas_w(i) * 10000.0) / 10000.0 + "\t");
            }
            System.out.println();
        }
    }


}
