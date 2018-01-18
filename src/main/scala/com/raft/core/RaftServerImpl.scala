package com.raft.core

import java.util.Collections

import com.raft.core.ServerRole.ServerRole

class RaftServerImpl extends RaftServer {
  private var serverId: Long = 0L
  private var peerIds: List[Long] = null
  private[raft] var role: ServerRole = ServerRole.Follower
  private[raft] var term: Long = 0
  private[raft] val previousLogIndex: Long = 0L
  private[raft] val previousLogTerm: Long = 0L
  private[raft] val commitIndex: Long = 0L
  private[raft] var stateRepository: StateRepository = null
  private var raftGateway: RaftGateway = null

  def this(serverId: Long, peerIds: List[Long], stateRepository: StateRepository, raftGateway: RaftGateway) {
    this()
    this.serverId = serverId
    this.peerIds = peerIds
    this.stateRepository = stateRepository
    this.raftGateway = raftGateway
  }

  //for test
  def this(id: Long, peers: List[Long], role: ServerRole, term: Long, stateRepository: StateRepository, raftGateway: RaftGateway) {
    this()
    this.serverId = id
    this.role = role
    this.term = term
    this.peerIds = peers
    this.stateRepository = stateRepository
    this.raftGateway = raftGateway
  }

  def heartbitTimeout(): Unit = {
  }

  def electionTimeout(): Unit = {
    startElection()
  }

  def startElection(): Unit = {
    term = term + 1
    role = ServerRole.Candidate
    val votes: List[VoteResponse] = requestVotes
    if (mejorityGranted(votes)) {
      this.role = ServerRole.Leader
      notifyPeers()
    }
  }

  private def notifyPeers(): Unit = {
    for (peer <- peerIds) {
      raftGateway.appendEntries(peer, new AppendEntry(term, serverId, previousLogIndex, previousLogTerm, Collections.emptyList(), commitIndex))
    }
  }

  private def requestVotes: List[VoteResponse] = {
    val neighboursVotes: List[VoteResponse] = requestVoteFromPeers
    var allVotes: List[VoteResponse] = neighboursVotes
    allVotes = allVotes.::(selfVote)
    allVotes
  }

  private def mejorityGranted(totalVotes: List[VoteResponse]): Boolean = {
    val grantedVotes: List[VoteResponse] = totalVotes.filter((vote: VoteResponse) => vote.isGranted)
    grantedVotes.size > (totalVotes.size / 2)
  }

  private def requestVoteFromPeers: List[VoteResponse] = {
    var responses: List[VoteResponse] = List[VoteResponse]()
    for (neighbour <- peerIds) {
      val voteResponse: VoteResponse = requestVoteFrom(neighbour)
      responses = responses.::(voteResponse)
    }
    responses
  }

  private def selfVote: VoteResponse = { //??selfVote;
    VoteResponse.granted(this.term)
  }

  def requestVoteFrom(otherServer: Long): VoteResponse = {
    val voteRequest: RequestVote = new RequestVote(term, this.serverId, 0, 0)
    raftGateway.requestVote(otherServer, voteRequest)
  }

  override def requestVote(voteRequest: RequestVote): VoteResponse = VoteResponse.granted(term)

  override def appendEntries(appendEntry: AppendEntry): AppendEntryResponse = {
    if ((this.role eq ServerRole.Candidate) && appendEntry.getTerm > this.term) this.role = ServerRole.Follower
    new AppendEntryResponse
  }
}