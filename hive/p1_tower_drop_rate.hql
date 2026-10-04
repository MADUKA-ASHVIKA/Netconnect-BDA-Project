USE netconnect;

SELECT
    towerId,
    COUNT(*) AS total_calls,
    SUM(CASE WHEN dropped = 'Y' THEN 1 ELSE 0 END) AS dropped_calls,
    ROUND(
        SUM(CASE WHEN dropped = 'Y' THEN 1 ELSE 0 END) / COUNT(*),
        4
    ) AS drop_rate
FROM cdr
WHERE callId <> 'callId'
GROUP BY towerId
ORDER BY towerId;