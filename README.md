# NetConnect Telecom CDR Analytics

A Big Data Analytics project for analyzing **telecom Call Detail Records (CDR)** using **Apache Hadoop MapReduce, Hive, Pig, HDFS, YARN, and Docker**.

The project processes telecom call records to identify tower-wise call performance, dropped-call rates, average call duration, call volumes, peak calling hours, and plan-wise drop proportions.

---

## 📌 Project Overview

Telecom networks generate a large volume of Call Detail Records (CDRs). Analyzing this data helps identify:

* Towers with high dropped-call rates
* Calling patterns across different customer plans
* Average call duration
* High-traffic towers
* Peak calling hours
* Customer-plan-wise call drop proportions

This project demonstrates how the same telecom dataset can be processed using different Big Data technologies.

### Technologies Used

| Technology    | Purpose                            |
| ------------- | ---------------------------------- |
| Java          | MapReduce implementation           |
| Apache Hadoop | Distributed storage and processing |
| HDFS          | Distributed file storage           |
| MapReduce     | Batch processing                   |
| Hive          | SQL-based analytics                |
| Apache Pig    | Data-flow based analytics          |
| YARN          | Resource management                |
| Docker        | Containerized Hadoop environment   |
| Maven         | Java project build                 |

---

# 📂 Project Structure

```text
Netconnect-BDA-Project/
│
├── config/
│   └── hadoop/
│       ├── core-site.xml
│       ├── hdfs-site.xml
│       ├── mapred-site.xml
│       ├── yarn-site.xml
│       └── hive-site.xml
│
├── data/
│   └── cdr.csv
│
├── hive/
│   └── p1_tower_drop_rate.hql
│
├── lib/
│   └── commons-collections-3.2.2.jar
│
├── pig/
│   └── p1_tower_drop_rate.pig
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── netconnect/
│                   ├── Problem 1
│                   ├── Problem 2
│                   ├── Problem 3
│                   ├── Problem 4
│                   └── Problem 5
│
├── docker-compose.yml
├── pom.xml
└── README.md
```

> The exact Java class names are organized under `src/main/java/com/netconnect/`.

---

# 📊 Dataset

The project uses a telecom CDR dataset stored in:

```text
data/cdr.csv
```

### Dataset fields

```text
callId,towerId,callerPlan,durationSec,dropped,timestamp
```

| Field         | Description                                         |
| ------------- | --------------------------------------------------- |
| `callId`      | Unique identifier of a call                         |
| `towerId`     | ID of the cellular tower handling the call          |
| `callerPlan`  | Customer plan such as Prepaid, Postpaid, or Premium |
| `durationSec` | Call duration in seconds                            |
| `dropped`     | Whether the call was dropped (`Y`/`N`)              |
| `timestamp`   | Date and time of the call                           |

### Dataset size

The dataset contains:

* **2000 call records**
* **10 towers**
* **3 caller plans**
* Dropped-call indicator using `Y/N`

---

# 🔎 Problems Implemented

## Problem 1 — Tower-wise Drop Rate

Calculates the total number of calls, dropped calls, and drop rate for each tower.

### Output

```text
Tower ID    Total Calls    Dropped Calls    Drop Rate
T01         186            12               0.0645
T02         192            13               0.0677
T03         200            15               0.0750
T04         196            19               0.0969
T05         173            16               0.0925
T06         212            20               0.0943
T07         195            11               0.0564
T08         232            15               0.0647
T09         204            19               0.0931
T10         210            14               0.0667
```

### Key finding

* **Highest drop rate:** T04 — **9.69%**
* **Lowest drop rate:** T07 — **5.64%**

---

# Problem 2 — Average Call Duration by Plan

Calculates the average call duration for each customer plan.

### Results

```text
Postpaid    473.64 seconds
Premium     485.49 seconds
Prepaid     464.13 seconds
```

### Key finding

* **Premium** customers have the highest average call duration.
* **Prepaid** customers have the lowest average call duration.

---

# Problem 3 — Tower-wise Call Volume

Calculates the total number of calls handled by each tower.

### Results

```text
T01    186
T02    192
T03    200
T04    196
T05    173
T06    212
T07    195
T08    232
T09    204
T10    210
```

### Key finding

* **Highest call volume:** T08 — **232 calls**
* **Lowest call volume:** T05 — **173 calls**
* **Total calls:** **2000**

---

# Problem 4 — Peak Calling Hour

Analyzes the timestamp field to determine the number of calls made during each hour of the day.

### Results

```text
00    80
01    86
02    73
03    80
04    75
05    93
06    83
07    76
08    84
09    87
10    80
11    98
12    75
13    95
14    82
15    95
16    78
17    87
18    70
19    90
20    96
21    77
22    86
23    74
```

### Key finding

* **Peak calling hour:** **11:00**
* **Number of calls:** **98**

---

# Problem 5 — Plan-wise Drop Proportion

Calculates dropped calls and drop proportion for each customer plan.

### Results

```text
Plan        Dropped Calls    Total Calls    Drop Rate
Postpaid    47               604            0.0778
Premium     20               298            0.0671
Prepaid     87               1098           0.0792
```

### Key finding

* **Highest drop proportion:** Prepaid — **7.92%**
* **Lowest drop proportion:** Premium — **6.71%**

---

# 🐝 Hive Analytics

Hive is used to perform SQL-based analysis over the CDR dataset stored in HDFS.

The Hive configuration is available in:

```text
config/hadoop/hive-site.xml
```

The Hive script for Problem 1 is available at:

```text
hive/p1_tower_drop_rate.hql
```

### Hive table

The CDR data is represented using the following schema:

```sql
CREATE EXTERNAL TABLE cdr (
    callId STRING,
    towerId STRING,
    callerPlan STRING,
    durationSec INT,
    dropped STRING,
    callTime STRING
)
```

The table reads the CDR data directly from HDFS.

### Hive analysis

The Hive query calculates:

* Total calls per tower
* Dropped calls per tower
* Drop rate per tower

The Hive results were verified against the MapReduce implementation.

---

# 🐷 Pig Analytics

Apache Pig is used to perform data-flow based analytics on the same CDR dataset.

Pig script:

```text
pig/p1_tower_drop_rate.pig
```

The script performs the following operations:

1. Loads the CDR file from HDFS.
2. Removes the CSV header.
3. Creates a drop flag for dropped calls.
4. Groups records by tower.
5. Calculates total calls.
6. Calculates dropped calls.
7. Calculates drop rate.
8. Orders the result by tower ID.

The Pig output matches the MapReduce and Hive results for the tower-wise drop-rate analysis.

---

# 🐳 Docker Environment

The project uses Docker Compose to create a multi-container Hadoop environment.

### Containers

```text
netconnect-namenode
netconnect-datanode
netconnect-resourcemanager
netconnect-nodemanager
netconnect-hive
netconnect-pig
```

### Architecture

```text
                    ┌─────────────────────┐
                    │      CDR Dataset    │
                    │       cdr.csv       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        HDFS         │
                    │      NameNode       │
                    │      DataNode       │
                    └──────────┬──────────┘
                               │
             ┌─────────────────┼─────────────────┐
             │                 │                 │
             ▼                 ▼                 ▼
       ┌───────────┐     ┌───────────┐     ┌───────────┐
       │ MapReduce │     │   Hive    │     │    Pig    │
       └─────┬─────┘     └─────┬─────┘     └─────┬─────┘
             │                 │                 │
             └─────────────────┼─────────────────┘
                               ▼
                    ┌─────────────────────┐
                    │   Analytics Results │
                    └─────────────────────┘

              YARN ResourceManager
                      │
                      ▼
                 NodeManager
```

---

# ⚙️ Prerequisites

Install the following:

* Docker Desktop
* Git
* Java JDK
* Apache Maven

Verify Docker:

```powershell
docker --version
docker compose version
```

Verify Java:

```powershell
java -version
```

Verify Maven:

```powershell
mvn -version
```

---

# 🚀 Setup

Clone the repository:

```bash
git clone https://github.com/MADUKA-ASHVIKA/Netconnect-BDA-Project.git
```

Move into the project directory:

```bash
cd Netconnect-BDA-Project
```

Start the Hadoop environment:

```bash
docker compose up -d
```

Check running containers:

```bash
docker ps
```

---

# 📥 Load Dataset into HDFS

Create the input directory:

```bash
docker exec netconnect-namenode hdfs dfs -mkdir -p /input
```

Copy the dataset into the NameNode container:

```bash
docker cp data/cdr.csv netconnect-namenode:/tmp/cdr.csv
```

Upload it to HDFS:

```bash
docker exec netconnect-namenode hdfs dfs -put -f /tmp/cdr.csv /input/cdr.csv
```

Verify:

```bash
docker exec netconnect-namenode hdfs dfs -ls /input
```

Check the data:

```bash
docker exec netconnect-namenode hdfs dfs -cat /input/cdr.csv
```

---

# 🔨 Build the Java MapReduce Project

Build the project using Maven:

```bash
mvn clean package
```

The generated JAR will be available under:

```text
target/
```

The project JAR is:

```text
netconnect-cdr-1.0.jar
```

---

# ▶️ Running MapReduce Jobs

The MapReduce implementations are available under:

```text
src/main/java/com/netconnect/
```

The five MapReduce problems analyze:

```text
P1 → Tower Drop Rate
P2 → Average Duration by Plan
P3 → Tower Call Volume
P4 → Peak Calling Hour
P5 → Plan Drop Proportion
```

After building the project, copy the generated JAR to the Hadoop container:

```bash
docker cp target/netconnect-cdr-1.0.jar netconnect-namenode:/tmp/netconnect-cdr-1.0.jar
```

The corresponding problem driver can then be executed using the Hadoop environment and the HDFS input:

```text
/input/cdr.csv
```

Each problem writes its result to a separate HDFS output directory.

---

# 🐝 Running Hive

The Hive container is started automatically through Docker Compose.

Check the Hive container:

```bash
docker ps
```

Enter Hive:

```bash
docker exec -it netconnect-hive beeline -u jdbc:hive2://localhost:10000
```

Use the project database:

```sql
USE netconnect;
```

The Hive script is available at:

```text
hive/p1_tower_drop_rate.hql
```

The Hive analysis produces the same tower-wise drop-rate result as MapReduce.

---

# 🐷 Running Pig

Verify Pig:

```bash
docker exec netconnect-pig pig -version
```

Copy the Pig script:

```bash
docker cp pig/p1_tower_drop_rate.pig netconnect-pig:/tmp/p1_tower_drop_rate.pig
```

Run the script:

```bash
docker exec -it netconnect-pig pig -x mapreduce /tmp/p1_tower_drop_rate.pig
```

The result is displayed using:

```text
DUMP
```

and can be compared with the MapReduce and Hive outputs.

---

# 📈 Final Results Summary

| Problem | Analysis             | Key Result                                   |
| ------- | -------------------- | -------------------------------------------- |
| P1      | Tower Drop Rate      | T04 has the highest drop rate — 9.69%        |
| P2      | Average Duration     | Premium has the highest average — 485.49 sec |
| P3      | Tower Call Volume    | T08 has the highest volume — 232 calls       |
| P4      | Peak Calling Hour    | 11:00 has the highest volume — 98 calls      |
| P5      | Plan Drop Proportion | Prepaid has the highest rate — 7.92%         |
| P6      | Hive & Pig           | Results verified against MapReduce           |

---

# 🔄 Technology Comparison

| Technology | Approach                    | Purpose                       |
| ---------- | --------------------------- | ----------------------------- |
| MapReduce  | Java-based batch processing | Distributed computation       |
| Hive       | SQL-based queries           | Easy analytical querying      |
| Pig        | Data-flow scripting         | ETL and analytical processing |
| HDFS       | Distributed storage         | Store large datasets          |
| YARN       | Resource management         | Manage cluster resources      |
| Docker     | Containerization            | Reproducible environment      |

---

# 🧠 Key Insights

Based on the analyzed dataset:

1. **Tower T04** has the highest dropped-call rate at approximately **9.69%**.
2. **Tower T08** handles the highest number of calls with **232 calls**.
3. **Premium** users have the highest average call duration at approximately **485.49 seconds**.
4. **11:00** is the peak calling hour with **98 calls**.
5. **Prepaid** users have the highest drop proportion at approximately **7.92%**.
6. The tower-wise drop-rate analysis produces consistent results across **MapReduce, Hive, and Pig**.

---

# 🛠️ Important Project Dependency

The Hadoop environment includes:

```text
lib/commons-collections-3.2.2.jar
```

This dependency is included in the repository because it is required by the Hadoop runtime configuration used by the project.

The JAR is mounted into the required Hadoop containers through:

```text
docker-compose.yml
```

---

# 🧹 Stopping the Environment

To stop the containers:

```bash
docker compose down
```

To stop the containers while preserving Docker volumes:

```bash
docker compose down
```

The project uses Docker named volumes for Hadoop and Hive data.

# 🎯 Learning Outcomes

This project demonstrates practical understanding of:

* Big Data processing
* Hadoop ecosystem
* HDFS
* Java MapReduce
* Mapper and Reducer concepts
* YARN
* Hive
* Apache Pig
* Docker-based Hadoop deployment
* Distributed data processing
* Telecom CDR analytics
* Comparing multiple Big Data processing approaches

---

# 📌 Conclusion

The **NetConnect Telecom CDR Analytics** project demonstrates how telecom Call Detail Records can be stored and analyzed using the Hadoop ecosystem.

The project implements multiple analytical problems using **Java MapReduce** and extends the analysis using **Hive and Pig**. Docker provides a reproducible environment containing the required Hadoop, YARN, Hive, and Pig services.

The results provide useful insights into tower performance, customer-plan behavior, call traffic, and peak usage patterns while demonstrating practical Big Data processing techniques.
