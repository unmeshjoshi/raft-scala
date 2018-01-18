package com.raft.core

import java.util
import java.util.List


class AppendEntry(var term: Long, var leader: Long,
                  var previousLogIndex: Long, var previousLogTerm: Long, var entries: List[LogEntry], var leaderCommitIndex: Long) {
  def getTerm: Long = term

  def getLeader: Long = leader

  def getPreviousLogIndex: Long = previousLogIndex

  def getPreviousLogTerm: Long = previousLogTerm

  def getEntries: util.List[LogEntry] = entries

  def getLeaderCommitIndex: Long = leaderCommitIndex
}
