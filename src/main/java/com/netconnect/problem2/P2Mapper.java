package com.netconnect.problem2;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class P2Mapper extends Mapper<Object, Text, Text, DoubleWritable> {

    private final Text plan = new Text();
    private final DoubleWritable duration = new DoubleWritable();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        if (line.startsWith("callId")) {
            return;
        }

        String[] fields = line.split(",");

        if (fields.length >= 6) {
            String callerPlan = fields[2];
            double durationSec = Double.parseDouble(fields[3]);

            plan.set(callerPlan);
            duration.set(durationSec);

            context.write(plan, duration);
        }
    }
}
