package org.umang.data.benchmarking.service.execute;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.umang.data.benchmarking.model.Operation;
import org.umang.data.benchmarking.service.record.RecordService;
import org.umang.data.benchmarking.service.workload.WorkloadService;
import org.umang.data.engine.KeyValueStore;

public class ExecutorService {

  private final KeyValueStore keyValueStore;
  private final WorkloadService workloadService;
  private final RecordService recordService;
  private final long operationCount;

  public ExecutorService(KeyValueStore keyValueStore, WorkloadService workloadService,
      RecordService recordService, int operationCount) {
    this.keyValueStore = keyValueStore;
    this.workloadService = workloadService;
    this.recordService = recordService;
    this.operationCount = operationCount;
  }

  public void execute() throws IOException {
    for (int i = 0; i < operationCount; i++) {
      Operation operation = workloadService.next();
      executeOperation(operation);
      recordService.record(operation, i);
    }
  }

  public void executeOperation(Operation operation) throws IOException {
    ByteBuffer key = ByteBuffer.wrap(operation.getKey().getBytes());
    switch (operation.getType()) {
      case PUT -> keyValueStore.put(key, ByteBuffer.wrap(operation.getValue().getBytes()));
      case GET -> keyValueStore.get(key);
      case DELETE -> keyValueStore.delete(key);
    }
  }
}
