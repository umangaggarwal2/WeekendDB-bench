package org.umang.data.benchmarking.model;

import java.util.Objects;

public class Operation {

  private final OperationType type;
  private final String key;
  private String value;

  public Operation(OperationType type, String key, String value) {
    this.type = type;
    this.key = key;
    this.value = value;
  }

  public OperationType getType() {
    return type;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operation operation = (Operation) o;
    return type == operation.type && Objects.equals(key, operation.key)
        && Objects.equals(value, operation.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, key, value);
  }

  @Override
  public String toString() {
    return "Operation{" +
        "type=" + type +
        ", key='" + key + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
