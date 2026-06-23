package org.umang.data.benchmarking.service.record;

import java.io.IOException;
import org.umang.data.pager.engine.EngineState;
import org.umang.data.pager.engine.StateRegistry;

public class SnapshotService {

  private final StateRegistry stateRegistry;

  public SnapshotService(StateRegistry stateRegistry) {
    this.stateRegistry = stateRegistry;
  }

  public EngineState snapshot() throws IOException {
    return stateRegistry.state();
  }
}
