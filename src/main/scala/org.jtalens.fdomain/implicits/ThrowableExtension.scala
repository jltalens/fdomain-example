package org.jtalens.fdomain.implicits

import java.io.{ PrintWriter, StringWriter }

object ThrowableExtension {

  implicit class ThrowableToString(val ex: Throwable) extends AnyVal {
    def throwToString = {
      val sw = new StringWriter
      ex.printStackTrace(new PrintWriter(sw))
      sw.toString
    }
  }

}