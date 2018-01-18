package com.raft.core

class RequestVote(var term: Long, var raftServerId: Long, //possibly club as logentry
                  var lastLogIndex: Long, var lastLogTerm: Long) {
}
