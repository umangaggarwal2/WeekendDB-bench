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
    List<Long> readLatencies =
        runtimeStates.stream().map(RuntimeState::lastReadLatencyNanos).sorted().toList();
    List<Long> writeLatencies =
        runtimeStates.stream().map(RuntimeState::lastWriteLatencyNanos).sorted().toList();
    List<Long> flushLatencies =
        runtimeStates.stream().map(RuntimeState::lastFlushLatencyNanos).sorted().toList();
    System.out.println("Read Latency P50: " + percentile(readLatencies, 50));
    System.out.println("Write Latency P50: " + percentile(writeLatencies, 50));
    System.out.println("Flush Latency P50: " + percentile(flushLatencies, 50));

    System.out.println("Read Latency P90: " + percentile(readLatencies, 90));
    System.out.println("Write Latency P90: " + percentile(writeLatencies, 90));
    System.out.println("Flush Latency P90: " + percentile(flushLatencies, 90));

    System.out.println("Read Latency P95: " + percentile(readLatencies, 95));
    System.out.println("Write Latency P95: " + percentile(writeLatencies, 95));
    System.out.println("Flush Latency P95: " + percentile(flushLatencies, 95));

    System.out.println("Read Latency P99: " + percentile(readLatencies, 99));
    System.out.println("Write Latency P99: " + percentile(writeLatencies, 99));
    System.out.println("Flush Latency P99: " + percentile(flushLatencies, 99));
  }

  private static long percentile(List<Long> sorted, double p) {
    if (sorted.isEmpty()) {
      throw new IllegalArgumentException("Empty list");
    }

    int index = (int) Math.ceil((p / 100.0) * sorted.size()) - 1;

    index = Math.clamp(index, 0, sorted.size() - 1);

    return sorted.get(index);
  }
}
