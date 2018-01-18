package com.raft.core

trait RaftGateway {
  def requestVote(serverId: Long, voteRequest: RequestVote): VoteResponse

  def appendEntries(serverId: Long, appendEntry: AppendEntry): AppendEntryResponse
}
