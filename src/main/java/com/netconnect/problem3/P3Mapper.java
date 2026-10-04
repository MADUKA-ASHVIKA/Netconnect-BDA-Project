package com.netconnect.problem3;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class P3Mapper extends Mapper<Object, Text, Text, IntWritable> {

    private final Text towerId = new Text();
    private final IntWritable one = new IntWritable(1);

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        if (line.startsWith("callId")) {
            return;
        }

        String[] fields = line.split(",");

        if (fields.length >= 6) {
            towerId.set(fields[1]);
            context.write(towerId, one);
        }
    }
}
