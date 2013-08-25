package com.seantheprogrammer.cigar_finder_android

import android.content.{Intent, Context}

class CallStore(context: Context, store: Store) extends ActivityProcess(context) {
  override def action = Intent.ACTION_CALL
  override def uri = "tel:" + store.phoneNumber
}
