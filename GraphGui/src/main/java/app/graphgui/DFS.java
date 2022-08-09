package app.graphgui;

import javafx.scene.control.Alert;
import java.util.ArrayList;

public class DFS {
    private ArrayList<Integer> path = new ArrayList<Integer>();
    private Aler aler1 = new Aler(Alert.AlertType.ERROR);
    private double pathValue;
    private static boolean isPathCorrect = true;

    public double getPathValue(){
       return Math.round(pathValue * 10000.0) / 10000.0;
   }

    public ArrayList<Integer> getPath(){
    return path;
   }

    @Override
    public String toString(){

        StringBuilder string = new StringBuilder();
        string.append("Droga to: ");
        for(int x = 0; x < path.size() - 1; x++){
            string.append(path.get(x) + "<-");
        }
        string.append(path.get(path.size()-1));
        return string.toString();
    }


    public void dij(Graph g1, int start, int end) {
        if(g1 == null) {
            System.err.println("Pusty graf!");
            aler1.alertText("In Ordnung!", "Graph error", "Graph is empty!");
        }

        if(start < 0 || end < 0 || start > g1.Getgsize() || end > g1.Getgsize()) {
            System.err.println("Beginning`s or last vertex`s value is unacceptable!");
           System.exit(1);
        }
        if(start == end){
            System.err.println("start and end is the same Vertex!");
            aler1.alertText("In Ordnung!", "Error", "start and end is the same Vertex!" );
        }

        int     pam   = g1.Getgsize();
        int     n2   = g1.Getgsize();
        int     p[] = new int    [pam];
        double  d[] = new double [pam];
        int     Q[] = new int    [pam];
        int     i, j, u = 0, w, guard;
        double weight;

        for( i = 0; i < pam; i++){
            p[i] = -1;
            d[i] = Double.MAX_VALUE;
            Q[i] = 0;
        }
        d[start] = 0;
        while(pam != 0){
            pam--;
            u = -1;
            for(i = 0; i < n2;i++)			//szukanie najmniejszej wagi krawedzi
            {
                if(u == -1 && Q[i] == 0)
                {   u = i;}
                else if(u != -1 && d[i] < d[u] && Q[i] == 0)
                {   u = i;}
            }
            Q[u] = 1;
            for(i = 0; i < g1.getWertex(u).getN();i++)
            {
                w = g1.getWertex(u).Getsas(i);
                if(Q[w] == 1)
                    continue;
                weight = g1.getWertex(u).Getsas_w(i);
                if(d[w] > d[u] + weight){
                    d[w] = d[u] + weight;
                    p[w] = u;
                }
            }
        }
        if(d[end] == 0){
            System.err.println("Path equals 0!!!");
            aler1.alertText("In Ordnung!", "Error", "Path equals 0!" );
            isPathCorrect = false;
            return;
           // System.exit(5);
        }
        pathValue = d[end];
        System.out.println("Wartosc najkrotszej trasy wynosi: " + pathValue);
        System.out.println("Trasa: ");

        i = end;
        this.path.add(i);
        System.out.print(i);
        i=p[i];
        do{
            try {
                this.path.add(i);
                System.out.print("<-" + i);
                i = p[i];
                if( i == -1) break;
            }catch (ArrayIndexOutOfBoundsException e){
               // aler1.alertText("In ordnung!", "Wrong path!", "Start and end vertex is the same");
                isPathCorrect = false;
                break;
            }
            catch (IndexOutOfBoundsException en){
                //aler1.alertText("In ordnung!", "Wrong path!", "Start and end vertex is the same");
                isPathCorrect = false;
                break;
            }
        }while(i != start);

       if( i != -1) {
           this.path.add(i);
           System.out.println("<-" + i);
       }
       System.out.println();
    }
}
