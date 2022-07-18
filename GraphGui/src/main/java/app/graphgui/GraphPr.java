package app.graphgui;

import java.io.IOException;
import java.util.Objects;

public class GraphPr {
    public static void main(String[] args) throws IOException {
        if(args.length <= 1 || Objects.equals(args[1], "-help"))
        {
            System.out.println("Poprawne wywolanie do generowania to:");
            System.out.println("java GraphPr 1.-gene 2.[wiersze] 3.[kolumny]");
            System.out.println("4.[pocz przedzialu wag] 5.[koniec przedzialu wag]");
            System.out.println("6.spojnosc (1 - niespojny, 0 - spojny) 7.-file " );
            System.out.println("8.[plik] /// lub od 7. puste(wyrzut na ekran)");
            System.out.println();
            System.out.println("Poprawne wywolanie do sprawdzania spojnosci to:");
            System.out.println("java GraphPr 1.-coher 2.-file 3.[plik]                		   ");
            System.out.println();
            System.out.println("Poprawne wywolanie liczenia najkrotszej sciezki: 		   ");
            System.out.println("java GraphPr 1.-path 2.[pocz przedz]           			   ");
            System.out.println("3.[kon przedz] 4.-file 5.[plik]           			   ");
            System.exit(1);
        }

        if(Objects.equals(args[0], "-gene"))
        {
            if(args.length < 6)
            {
                System.err.println("Za mało argumentów!");
                System.exit(1);
            }

            try{
                int r = Integer.parseInt(args[1]);
                int c = Integer.parseInt(args[2]);
                double w_min = Double.parseDouble(args[3]);
                double w_max = Double.parseDouble(args[4]);
                int coher = Integer.parseInt(args[5]);

                if( r < 0 || c < 0){
                    System.out.println("Ujemne kolumny lub wiersze!!");
                    System.exit(1);
                }

                if(w_min < 0 || w_max < 0)
                {
                    System.out.println("Ujemna waga jest nieprawidlowa!!");
                    System.exit(1);
                }

                if(args.length == 8 && (Objects.equals(args[6], "-file") || Objects.equals(args[6], "-File")))
                {

                    Graph g1 = new Graph(r,c);
                    g1.gen_graph(r, c, w_min, w_max, coher);
                    Utils.GraphtoFile(g1, args[7]);
                }

                else if(args.length == 6){
                    Graph g1 = new Graph(r,c);
                    g1.gen_graph(r, c,  w_min, w_max, coher);
                    Utils.GraphOut(g1);
                }
            }
            catch(NumberFormatException e){
                System.err.println("Zly format kolumn  lub/i wierszy !!!");
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(Objects.equals(args[0], "-coher") || Objects.equals(args[0],"-Coher"))
        {
            if(args.length < 3) {
                System.err.println("Zbyt malo argumentow!");
                System.exit(1);
            }

            if(!Objects.equals(args[1], "-file"))
            {
                System.err.println("Zly argument");
                System.exit(1);
            }

            Graph g1 = new Graph();
            g1 = g1.ReadtoGraph(args[2]);
            if(BFS.out(g1) == 1){
                System.out.println("Graf Spojny");
                System.exit(0);
            }
            else{
                System.out.println("Graf Niespojny");
                System.exit(5);
            }
        }

        else if(Objects.equals(args[0],"-path") || Objects.equals(args[0],"-Path")){
            if(args.length < 5){
                System.err.println("Za malo argumentow");
                System.exit(1);
            }
            if(!Objects.equals(args[1],"-file")){
                System.err.println("argumentem nr 2 powinna byc flaga: -file ");
                System.exit(1);
            }
            int start = Integer.parseInt(args[3]);
            int end   = Integer.parseInt(args[4]);

            Graph g1 = new Graph();
            g1 = g1.ReadtoGraph(args[2]);
            if(BFS.out(g1) == 0)
            {
                System.out.println("Graf niespojny! Nie wykonano szukania sciezki");
                System.exit(5);
            }
            DFS.dij(g1, start, end);
        }


        else{
            System.out.println("Niepoprawny argument");
            System.exit(1);
        }
    }
}
