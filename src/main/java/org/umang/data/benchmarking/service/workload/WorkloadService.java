package org.umang.data.benchmarking.service.workload;

import java.util.random.RandomGenerator;
import org.umang.data.benchmarking.model.Operation;
import org.umang.data.benchmarking.model.OperationType;
import org.umang.data.benchmarking.service.data.ByteGenerator;
import org.umang.data.benchmarking.service.data.KeySpace;

public class WorkloadService {

  private final RandomGenerator random = RandomGenerator.of("L32X64MixRandom");
  private final KeySpace keySpace;

  public WorkloadService(KeySpace keySpace) {
    this.keySpace = keySpace;
  }

  public Operation next() {
    return randomOperation();
  }

  private Operation randomOperation() {
    if (keySpace.isEmpty()) {
      return putOperation();
    }
    OperationType type = randomType();
    return switch (type) {
      case GET -> getOperation();
      case PUT -> putOperation();
      case DELETE -> deleteOperation();
    };
  }

  /**
   * Produce a random operation type - get, put, delete. Probability of each operation is defined as
   * GET = 60%, PUT = 35%, DELETE = 5%.
   *
   * @return OperationType.GET, OperationType.PUT, OperationType.DELETE
   */
  private OperationType randomType() {
    int value = random.nextInt(100);
    if (value < 60) {
      return OperationType.GET;
    } else if (value < 95) {
      return OperationType.PUT;
    }
    return OperationType.DELETE;
  }

  private Operation getOperation() {
    return new Operation(OperationType.GET, keySpace.random(random), null);
  }

  private Operation putOperation() {
    byte[] key = putKey();
    return new Operation(OperationType.PUT, key, ByteGenerator.INSTANCE.generateRandomLength());
  }

  private Operation deleteOperation() {
    byte[] key = keySpace.random(random);
    keySpace.remove(key);
    return new Operation(OperationType.DELETE, key, null);
  }

  /**
   * Produce a random key - could be existing or new. Probability of existing = 70%, new 30%.
   *
   * @return key strings, e.g. key-123
   */
  private byte[] putKey() {
    boolean overwrite = !keySpace.isEmpty() && random.nextInt(100) < 70;
    if (overwrite) {
      return keySpace.random(random);
    }
    byte[] key = ByteGenerator.INSTANCE.generate(8);
    keySpace.add(key);
    return key;
  }
}
