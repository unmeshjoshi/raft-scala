package com.raft.core

object ServerRole extends Enumeration {
  type ServerRole = Value
  val Candidate, Leader, Follower = Value
}
