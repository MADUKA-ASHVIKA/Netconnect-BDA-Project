package com.netconnect.problem4;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class P4Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private final IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int totalCalls = 0;

        for (IntWritable value : values) {
            totalCalls += value.get();
        }

        result.set(totalCalls);
        context.write(key, result);
    }
}
