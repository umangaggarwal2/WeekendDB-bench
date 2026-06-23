package org.umang.data.benchmarking;

import java.io.IOException;
import org.umang.data.benchmarking.service.data.KeySpace;
import org.umang.data.benchmarking.service.execute.ExecutorService;
import org.umang.data.benchmarking.service.record.RecordService;
import org.umang.data.benchmarking.service.record.SnapshotService;
import org.umang.data.benchmarking.service.workload.WorkloadService;
import org.umang.data.pager.engine.StorageEngine;

public class Main {

  static void main(String[] args) throws IOException {
    int operationCount = 100000000;
    StorageEngine storageEngine = StorageEngine.open("benchmarking-pager-1");
    WorkloadService workloadService = new WorkloadService(new KeySpace());
    SnapshotService snapshotService = new SnapshotService(storageEngine);
    RecordService recordService = new RecordService(snapshotService, operationCount, 100);
    ExecutorService executorService =
        new ExecutorService(storageEngine, workloadService, recordService, operationCount);
    executorService.execute();
    recordService.results();
  }
}
