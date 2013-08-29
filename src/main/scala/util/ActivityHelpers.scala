package com.seantheprogrammer.cigar_finder_android

import org.scaloid.common.SIntent
import android.app.PendingIntent
import scala.reflect.ClassTag
import android.content.{Intent, Context}

trait ActivityHelpers {
  @inline def pendingActivity(intent: Intent, flags: Int)(implicit context: Context) =
    PendingIntent.getActivity(context, 0, intent, flags)

  @inline def pendingActivity[T](flags: Int)(implicit context: Context, mt: ClassTag[T]) =
    PendingIntent.getActivity(context, 0, SIntent[T], flags)
}

object ActivityHelpers extends ActivityHelpers
