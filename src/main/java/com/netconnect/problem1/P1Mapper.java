package com.netconnect.problem1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class P1Mapper extends Mapper<Object, Text, Text, IntWritable> {

    private final Text towerId = new Text();
    private final IntWritable dropped = new IntWritable();

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        // Skip CSV header
        if (line.startsWith("callId")) {
            return;
        }

        String[] fields = line.split(",");

        if (fields.length != 6) {
            return;
        }

        towerId.set(fields[1]);

        // 1 = dropped call, 0 = successful call
        if (fields[4].equalsIgnoreCase("Y")) {
            dropped.set(1);
        } else {
            dropped.set(0);
        }

        context.write(towerId, dropped);
    }
}
