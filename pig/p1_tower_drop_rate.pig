records = LOAD 'hdfs://namenode:8020/input/cdr.csv'
          USING PigStorage(',')
          AS (
              callId:chararray,
              towerId:chararray,
              callerPlan:chararray,
              durationSec:int,
              dropped:chararray,
              callTime:chararray
          );

data = FILTER records BY callId != 'callId';

with_flag = FOREACH data GENERATE
             towerId,
             (dropped == 'Y' ? 1 : 0) AS dropFlag;

grouped = GROUP with_flag BY towerId;

result = FOREACH grouped GENERATE
         group AS towerId,
         COUNT(with_flag) AS total_calls,
         SUM(with_flag.dropFlag) AS dropped_calls,
         (double)SUM(with_flag.dropFlag) / COUNT(with_flag) AS drop_rate;

ordered = ORDER result BY towerId;

DUMP ordered;