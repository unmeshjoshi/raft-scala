package com.raft.core

import java.util

class PersistentState {
  private[raft] val log: util.Map[Integer, LogEntry] = new util.HashMap[Integer, LogEntry]
  private[raft] val votedForCandidateId: Long = 0L
  private[raft] val currentTerm: Long = 0L
}

