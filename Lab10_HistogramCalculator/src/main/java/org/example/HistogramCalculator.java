package org.example;

public class HistogramCalculator implements  Runnable{
    long[] histro;
    int start;
    int end;
    int[] data;

    public HistogramCalculator(int[] data,int start, int end, int range) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.histro = new long[range];
    }

    @Override
    public void run() {
        for(int i=start; i< end; i++){
            histro[data[i]]++;
        }
    }
    public long[] getHistogram(){
        return this.histro;
    }
}
