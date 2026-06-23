package org.umang.data.benchmarking.model;

import java.time.LocalDateTime;
import java.util.Objects;
import org.umang.data.pager.engine.EngineState;

public final class OperationRecord {

  private final OperationType type;
  private final LocalDateTime localDateTime;
  private final EngineState engineState;

  public OperationRecord(OperationType type, LocalDateTime localDateTime, EngineState engineState) {
    this.type = type;
    this.localDateTime = localDateTime;
    this.engineState = engineState;
  }

  public OperationType getType() {
    return type;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public EngineState getEngineState() {
    return engineState;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationRecord that = (OperationRecord) o;
    return type == that.type && Objects.equals(localDateTime, that.localDateTime) && Objects.equals(
        engineState, that.engineState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, localDateTime, engineState);
  }

  @Override
  public String toString() {
    return "OperationRecord{" + "type=" + type + ", localDateTime=" + localDateTime
        + ", engineState=" + engineState + '}';
  }
}
