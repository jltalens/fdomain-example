package org.jtalens.fdomain.services.io

import cats.effect.IO
import org.jtalens.fdomain.model.{ AccountSummary, UserID }
import org.jtalens.fdomain.services.AccountSummaryService

import scala.io.Source
import scala.util.{ Failure, Success, Try }

object AccountSummaryServiceFile extends AccountSummaryService with AccountSummaryParsers {

  import org.jtalens.fdomain.implicits.ThrowableExtension._

  def accountSummary(userId: UserID): IO[Either[String, AccountSummary]] = {
    val parsingResult = Try(Source.fromResource("account_summary.json").mkString) match {
      case Failure(ex) =>
        Left(ex.throwToString)
      case Success(f) =>
        fromString(f)(userId)
    }
    IO(parsingResult)
  }
}

