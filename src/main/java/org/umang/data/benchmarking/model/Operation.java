package org.umang.data.benchmarking.model;

import java.util.Arrays;
import java.util.Objects;

public class Operation {

  private final OperationType type;
  private final byte[] key;
  private final byte[] value;

  public Operation(OperationType type, byte[] key, byte[] value) {
    this.type = type;
    this.key = key;
    this.value = value;
  }

  public OperationType getType() {
    return type;
  }

  public byte[] getKey() {
    return key;
  }

  public byte[] getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operation operation = (Operation) o;
    return type == operation.type && Objects.equals(key, operation.key)
        && Arrays.equals(value, operation.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, key, Arrays.hashCode(value));
  }
}
