package com.seantheprogrammer.cigar_finder_android

import android.content.{Intent, Context}

class CallStore(context: Context, store: Store) extends ActivityProcess(context) {
  override def action = Intent.ACTION_CALL
  override def uri = "tel:" + store.phoneNumber.get

  override def run = store.phoneNumber match {
    case Some(phoneNumber) => super.run
    case None => //No phone number for store, do nothing
  }
}
