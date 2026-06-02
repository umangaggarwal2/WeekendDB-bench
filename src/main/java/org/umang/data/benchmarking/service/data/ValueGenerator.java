package org.umang.data.benchmarking.service.data;

import java.util.random.RandomGenerator;

public enum ValueGenerator {
  INSTANCE;

  private final String ALLOWED_CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
  private static final int MAX_LENGTH = 1024; // 1024 bytes
  private final RandomGenerator random = RandomGenerator.of("L32X64MixRandom");

  public String generateValue() {
    int targetLength = random.nextInt(1, MAX_LENGTH + 1);
    StringBuilder sb = new StringBuilder(targetLength);
    for (int i = 0; i < targetLength; i++) {
      int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
      sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
    }
    return sb.toString();
  }
}
