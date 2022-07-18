package app.graphgui;

public class Vert {
    private int id;
    private int[] sas = new int [1];
    private double[] sas_weight = new double [1];
    private int n = 0;


    public Vert(int id)
    { this.id = id;
    }

    public int getN(){
        return n;
    }

    public int Getsas(int i){
        return sas[i];
    }

    public double Getsas_w(int i){
        return sas_weight[i];
    }

    public void addSas(int sas1, double sas_weight1)
    {
        if(sas.length == n + 1)
            SizeOne();
        sas[n] = sas1;
        sas_weight[n] = sas_weight1;
        n++;
    }

    private void SizeOne()
    {
        int[] sasn = new int [sas.length + 1];
        double[] sas_weightn = new double [sas.length + 1];
        System.arraycopy(sas, 0, sasn, 0, sas.length);
        System.arraycopy(sas_weight, 0, sas_weightn, 0, sas_weight.length);
        sas = sasn;
        sas_weight = sas_weightn;
    }
 }
