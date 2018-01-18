package com.raft.core

object VoteResponse {
  def granted(currentTerm: Long) = new VoteResponse(currentTerm, true)

  def notGranted(currentTerm: Long) = new VoteResponse(currentTerm, false)
}

class VoteResponse(var currentTerm: Long, var voteGranted: Boolean) {
  def isGranted: Boolean = voteGranted

  def getCurrentTerm: Long = currentTerm
}
