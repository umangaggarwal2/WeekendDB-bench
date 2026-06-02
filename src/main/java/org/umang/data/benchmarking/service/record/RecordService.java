package org.umang.data.benchmarking.service.record;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.umang.data.benchmarking.model.Operation;
import org.umang.data.benchmarking.model.OperationRecord;
import org.umang.data.engine.EngineState;
import org.umang.data.engine.EngineState.RuntimeState;

public class RecordService {

  private final List<OperationRecord> operationRecords;
  private final SnapshotService snapshotService;
  private final int frequency;

  public RecordService(SnapshotService snapshotService, int operationCount, int frequency) {
    this.snapshotService = snapshotService;
    this.operationRecords = new ArrayList<>(operationCount);
    this.frequency = frequency;
  }

  public void record(Operation operation, int opCount) throws IOException {
    if (opCount % frequency == 0) {
      EngineState engineState = snapshotService.snapshot();
      OperationRecord operationRecord =
          new OperationRecord(operation.getType(), LocalDateTime.now(), engineState);
      operationRecords.add(operationRecord);
    }
  }

  public void results() {
    EngineState lastState = operationRecords.getLast().getEngineState();
    System.out.println("Pages = " + lastState.pageStoreState().pageCount());
    System.out.println("File Size = " + lastState.pageStoreState().fileSize());
    System.out.println(
        "Overflow Allocations = " + lastState.pageStoreState().performedOverflowAllocations());
    System.out.println("Max Chain Depth = " + lastState.pageStoreState().maxObservedChainDepth());
    System.out.println("Tombstone slots " + lastState.pageStoreState().createdTombstoneSlots());
    List<RuntimeState> runtimeStates =
        operationRecords.stream().map(OperationRecord::getEngineState)
            .map(EngineState::runtimeState).toList();
    double averageReadLatency =
        runtimeStates.stream().mapToDouble(RuntimeState::lastReadLatencyNanos).average()
            .getAsDouble();
    double averageWriteLatency =
        runtimeStates.stream().mapToDouble(RuntimeState::lastWriteLatencyNanos).average()
            .getAsDouble();
    double averageFlushLatency =
        runtimeStates.stream().mapToDouble(RuntimeState::lastFlushLatencyNanos).average()
            .getAsDouble();
    System.out.println("Read Latency " + averageReadLatency);
    System.out.println("Write Latency " + averageWriteLatency);
    System.out.println("Flush Latency " + averageFlushLatency);
//    System.out.println(runtimeStates.stream().map(RuntimeState::lastWriteLatencyNanos).filter(val -> val < 0).collect(
//        Collectors.toList()));
  }
}
