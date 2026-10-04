package com.netconnect.problem5;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class P5Reducer extends Reducer<Text, Text, Text, Text> {

    private Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        int totalCalls = 0;
        int droppedCalls = 0;

        for (Text value : values) {

            String[] parts = value.toString().split(",");

            totalCalls += Integer.parseInt(parts[0]);
            droppedCalls += Integer.parseInt(parts[1]);
        }

        double proportion = (double) droppedCalls / totalCalls;

        result.set(
                droppedCalls + "\t" +
                        totalCalls + "\t" +
                        String.format("%.4f", proportion));

        context.write(key, result);
    }
}