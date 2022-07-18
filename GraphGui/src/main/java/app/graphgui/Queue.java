package app.graphgui;

public class Queue {
    public int q[];
    private int n;

    public Queue() {
        q = new int [2];
        n = 0;
    }

    private void DoubleSize(){
        int [] nq = new int [2 * q.length];
        System.arraycopy(q,0,nq,0,q.length);
        q = nq;
    }

    public void add(int x){
        if(n == q.length)
            DoubleSize();
        q[n++] = x;
    }

    public int pop(){
        return q[--n];
    }

    public boolean sameAs( Queue o ) {
        if( this.n != o.n ) return false;
        for( int i = 0; i < n; i++ )
            if( this.q[i] != o.q[i] ) return false;
        return true;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int capacity() {
        return q.length;
    }
}

