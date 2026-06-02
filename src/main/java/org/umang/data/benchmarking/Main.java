package org.umang.data.benchmarking;

import java.io.IOException;
import java.nio.file.Path;
import org.umang.data.benchmarking.service.data.KeySpace;
import org.umang.data.benchmarking.service.execute.ExecutorService;
import org.umang.data.benchmarking.service.record.RecordService;
import org.umang.data.benchmarking.service.record.SnapshotService;
import org.umang.data.benchmarking.service.workload.WorkloadService;
import org.umang.data.engine.StorageEngine;

public class Main {

  static void main(String[] args) throws IOException {
    int operationCount = 100000;
    StorageEngine storageEngine = StorageEngine.open(Path.of("/tmp/benchmarking-db-3"));
    WorkloadService workloadService = new WorkloadService(new KeySpace());
    SnapshotService snapshotService = new SnapshotService(storageEngine);
    RecordService recordService = new RecordService(snapshotService, operationCount, 100);
    ExecutorService executorService =
        new ExecutorService(storageEngine, workloadService, recordService, operationCount);
    executorService.execute();
    recordService.results();
  }
}
