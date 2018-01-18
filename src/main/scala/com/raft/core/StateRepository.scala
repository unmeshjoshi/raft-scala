package com.raft.core

trait StateRepository {
  def get: PersistentState

  def update(state: PersistentState): PersistentState
}
