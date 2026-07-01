package org.umang.data.benchmarking.service.data;

import java.util.random.RandomGenerator;

public enum ByteGenerator {
  INSTANCE;

  private final String ALLOWED_CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
  private static final int MAX_LENGTH = 1024; // 1024 bytes
  private final RandomGenerator random = RandomGenerator.of("L32X64MixRandom");

  public byte[] generate(int len) {
    byte[] value = new byte[len];
    for (int i = 0; i < len; i++) {
      value[i] = (byte) ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length()));
    }
    return value;
  }

  public byte[] generateRandomLength() {
    int len = random.nextInt(1, MAX_LENGTH + 1);
    byte[] value = new byte[len];
    for (int i = 0; i < len; i++) {
      value[i] = (byte) ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length()));
    }
    return value;
  }
}
