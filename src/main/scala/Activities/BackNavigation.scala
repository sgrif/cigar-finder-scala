package com.seantheprogrammer.cigar_finder_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem

trait BackNavigation[T] extends Activity {
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    getActionBar.setDisplayHomeAsUpEnabled(true)
  }

  override def onOptionsItemSelected(item: MenuItem) = item.getItemId match {
    case android.R.id.home => {
      val intent = new Intent(this, parentActivity)
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
      navigateUpTo(intent)
      true
    }
    case _ => super.onOptionsItemSelected(item)
  }

  def parentActivity: Class[T]
}
