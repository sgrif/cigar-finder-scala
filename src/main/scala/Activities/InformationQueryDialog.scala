package com.seantheprogrammer.cigar_finder_android

import android.app.{AlertDialog, Activity, DialogFragment}
import android.os.Bundle
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener

class InformationQueryDialog extends DialogFragment {
  import InformationQueryDialog.Callbacks
  override def onAttach(activity: Activity) = {
    super.onAttach(activity)
    callbacks = activity match {
      case callbacks: Callbacks => callbacks
      case _ => DummyCallbacks
    }
  }

  override def onCreateDialog(b: Bundle) = {
    new AlertDialog.Builder(getActivity)
      .setTitle(searchResult.storeName)
      .setMessage("Does %s carry %s?"
        .format(searchResult.storeName, searchResult.cigar))
      .setPositiveButton("Yes", new OnClickListener {
        def onClick(dialog: DialogInterface, which: Int) = callbacks.reportCarried()
      })
      .setNegativeButton("No", new OnClickListener {
        def onClick(dialog: DialogInterface, which: Int) = callbacks.reportNotCarried()
      })
      .create()
  }

  override def onCancel(dialog: DialogInterface) = {
    super.onCancel(dialog)
    callbacks.dialogCanceled()
  }

  private lazy val searchResult = getArguments.getParcelable[SearchResult]("searchResult")

  private var callbacks: Callbacks = DummyCallbacks

  private object DummyCallbacks extends Callbacks {
    override def dialogCanceled() {}
    override def reportCarried() {}
    override def reportNotCarried() {}
  }
}

object InformationQueryDialog {
  def apply(result: SearchResult) = {
    val args = new Bundle
    args.putParcelable("searchResult", result)

    val fragment = new InformationQueryDialog
    fragment.setArguments(args)
    fragment
  }

  trait Callbacks {
    def dialogCanceled(): Unit
    def reportCarried(): Unit
    def reportNotCarried(): Unit
  }
}
