# PerformancePrediction
Integrated Hadoop YARN heterogenuous multi-framework Performance Prediction project

Sample Usages (default package: cn.hit.cst.ssl)

Prediction Test:

1. Use our general model to test job performance prediction with Hadoop YARN history data and visualize results

  predictor.Test

  java Test {inputPath} {trainModelIteration} {outputPath}

History Collector & Analyzer

1. Simplify spark history data

  historyanalyzer.control.HistoryUtils

  java HistoryUtils {inputPath} {outputPath}

2. Based on benchmark log files to extract YARN general job history info

  historyanalyzer.control.JobHistoryExtractor

  java JobHistoryExtractor {logFileDir} {dataOutputPath}

3. Based on benchmark log file to extract MR-specific job history data through MR REST API

  historyanalyzer.control.MapReduceExtractor

  java MapReduceExtractor {logFileDir} {dataOutputPath}

4. Based on benchmark log file to extract Spark-specific job history data through Spark REST API

  historyanalyzer.control.SparkHistoryExtractor

  java SparkHistoryExtractor {logFileDir} {dataOutputPath}

5. Merge spark raw history data from a executor a line to a job a line for further analysis

  historyanalyzer.control.SparkHistoryMerger

  java SparkHistoryMerger {rawFilePath} {dataOutputPath}

Benchmark:

1. Generate multi-framework job-heterogenuous benchmark shell scripts

  benchmark.WorkloadGenerator

  java WorkloadGenerator {targetDir} {totalJobCount} {tenantCount} {modeOptions}

  {modeOptions}: 1: generate fb probability workload; 2: generate test probability; 3. generate test all size
