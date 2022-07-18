package app.graphgui;

public class BFS {


    public static int out(Graph g1) {
        int i;
        int visited[] = new int[g1.Getgsize()];


        Queue q1 = new Queue();
        q1.add(0);
        visited[0] = 1;
        int v1;

        while (!q1.isEmpty()) {
            v1 = q1.pop();

            for (i = 0; i < g1.getWertex(v1).getN(); i++) {
                if (visited[g1.getWertex(v1).Getsas(i)] == 1)
                    continue;
                q1.add(g1.getWertex(v1).Getsas(i));
                visited[g1.getWertex(v1).Getsas(i)] = 1;
            }
        }

        int is_coherent = 1;
        for (i = 0; i < g1.Getgsize(); i++) {
            if (visited[i] == 0) {
                is_coherent = 0;
                break;
            }
        }
        if(is_coherent == 0 ){
            int l = 0;
            System.out.println("Nieodwiedzono: ");
            for (i = 0; i < g1.Getgsize(); i++) {
                if(l == 10){
                    System.out.println();
                    l = 0;
                }

                if (visited[i] == 0) {
                    System.out.print(i + "  ");
                    l++;
                }
            }
        }
        System.out.println();
        return is_coherent;
    }
}
