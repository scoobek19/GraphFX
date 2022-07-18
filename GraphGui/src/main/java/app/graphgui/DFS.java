package app.graphgui;

public class DFS {
    private int[] path;
    private double length;
    private int n1;

    public DFS(){
        this.path = new int [1];
        this.length = 0;
        this.n1 = 0;
    }

/*    private void DoubleSize(){
        int[] pathn = new int [path.length * 2];
        System.arraycopy(path, 0, pathn, 0, path.length);
        path = pathn;
    }

    private static int get_min_double_index(double d[], int n, int q[]) {
        int ind = -1;
        for (int i = 0; i < n; i++) {
            if (ind == -1 && q[i] == 0)
                ind = i;
            else if (d[i] < d[ind] && q[i] == 0)
                ind = i;
        }
        System.out.println(ind);
        return ind;
    }
*/

    public static void dij(Graph g1, int start, int end){
        if(g1 == null) {
            System.out.println("Pusty graf!");
            System.exit(1);
        }

        if(start < 0 || end < 0 || start > g1.Getgsize() || end > g1.Getgsize()) {
            System.out.println("Nieprawidlowy wierzcholek poczatkowy lub/i koncowy!");
            System.exit(1);
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
            System.err.println("Droga wynosi 0!!!");
            System.exit(5);
        }
        System.out.println("Wartosc najkrotszej trasy wynosi: " + d[end]);
        System.out.println("Trasa: ");

        i = end;
        System.out.print(i);
        i=p[i];
        do{
            System.out.print("<-" + i);
            i = p[i];
        }while(i != start);
        System.out.println("<-" + i);

    }
}
