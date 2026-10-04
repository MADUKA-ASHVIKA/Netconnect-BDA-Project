package com.netconnect.problem1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class P1Reducer extends Reducer<Text, IntWritable, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int totalCalls = 0;
        int droppedCalls = 0;

        for (IntWritable value : values) {
            totalCalls++;

            if (value.get() == 1) {
                droppedCalls++;
            }
        }

        double dropRate = (double) droppedCalls / totalCalls;

        String result = totalCalls + "\t"
                + droppedCalls + "\t"
                + String.format("%.4f", dropRate);

        context.write(key, new Text(result));
    }
}
