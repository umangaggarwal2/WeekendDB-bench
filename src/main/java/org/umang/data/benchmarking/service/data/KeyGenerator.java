package org.umang.data.benchmarking.service.data;

public enum KeyGenerator {
  INSTANCE;

  private static final String KEY_PREFIX = "key-";
  private long key = 1;

  public String generateKey() {
    return KEY_PREFIX + key++;
  }
}
