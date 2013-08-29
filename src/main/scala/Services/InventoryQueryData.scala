package com.seantheprogrammer.cigar_finder_android

import android.content.Context

class InventoryQueryData(implicit context: Context) {
  def timeSinceNotification = now - prefs.lastNotification(0L)
  def timeSinceInformationReceived = now - prefs.lastInformationReceived(0L)

  def notificationSent() {
    prefs.lastNotification = now
  }

  def informationReceived() {
    prefs.lastInformationReceived = now
  }

  def currentStoreIds = prefs.currentStoreIds(Set.empty)
  def currentStoreIds_=(storeIds: Set[String]) {
    prefs.currentStoreIds = storeIds
  }

  private lazy val prefs = Prefs()
  private def now = System.currentTimeMillis
}
