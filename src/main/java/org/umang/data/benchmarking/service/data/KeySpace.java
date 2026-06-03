package org.umang.data.benchmarking.service.data;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class KeySpace {

  private final List<String> keys = new ArrayList<>(150000);

  public void add(String key) {
    keys.add(key);
  }

  public void remove(String key) {
  }

  public String random(RandomGenerator random) {
    return keys.get(random.nextInt(keys.size()));
  }

  public boolean isEmpty() {
    return keys.isEmpty();
  }
}
