package com.raft.core

trait RaftServer {
  def requestVote(voteRequest: RequestVote): VoteResponse

  def appendEntries(appendEntry: AppendEntry): AppendEntryResponse
}

