package com.netconnect.problem5;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class P5Mapper extends Mapper<Object, Text, Text, Text> {

    private Text plan = new Text();
    private Text value = new Text();

    public void map(Object key, Text line, Context context)
            throws IOException, InterruptedException {

        String record = line.toString();

        // Skip header
        if (record.startsWith("callId")) {
            return;
        }

        String[] fields = record.split(",");

        String callerPlan = fields[2];

        // dropped field contains N or Y
        int dropped = fields[4].equalsIgnoreCase("Y") ? 1 : 0;

        plan.set(callerPlan);

        // Store: total call, dropped call
        value.set("1," + dropped);

        context.write(plan, value);
    }
}