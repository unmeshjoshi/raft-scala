package com.raft.core

import java.util.Collections

import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.ServerHandshakeStateEvent
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

class RaftServerTest extends FunSuite with Matchers with BeforeAndAfterAll {


  test("should Initialize Term at startup") {
    val raftServer = new RaftServerImpl(0, List(1, 2), new StubStateRepository(), new StubRaftGateway())
    assert(raftServer.term == 0)
  }

  test("should start in follower state") {
    val raftServer = new RaftServerImpl(0, List(1, 2), new StubStateRepository(), new StubRaftGateway())
    assert(raftServer.role == ServerRole.Follower)
  }

  test("shouldElectItselfIfSingleServer") {
    val server = new RaftServerImpl(0, List(), new StubStateRepository, new StubRaftGateway)
    server.startElection()
    assert(server.role == ServerRole.Leader)
    assert(1 == server.term)
  }

  test("should Become Leader If Gets Majority") {
    val server2 = new StubRaftGateway(VoteResponse.granted(1))
    val server1 = new RaftServerImpl(0, List(2), new StubStateRepository, server2)
    server1.startElection()
    assert(server1.role == ServerRole.Leader)
  }

}
