package org.jtalens.fdomain.services.io

import cats.effect.IO
import io.circe.parser._
import org.jtalens.fdomain.model.{AccountSummary, UserID}
import org.jtalens.fdomain.services.AccountSummaryService

import scala.io.Source
import scala.util.{Failure, Success, Try}

object AccountSummaryServiceFile extends AccountSummaryService {

  import org.jtalens.fdomain.implicits.ThrowableExtension._

  def accountSummary(userId: UserID): IO[Either[String, AccountSummary]] = {
    val parsingResult = Try(Source.fromResource("account_summary.json").mkString) match {
      case Failure(ex) =>
        Left(ex.throwToString)
      case Success(f) =>
        decodeAccountSummary(f)(userId)
    }
    IO(parsingResult)
  }

  private def decodeAccountSummary(content: String)(userID: UserID): Either[String, AccountSummary] =
    parse(content)
      .map(json => json.as[AccountSummary])
      .fold(
        parsingFailure => Left(parsingFailure.message),
        decoderResult => decoderResult.fold(
          decodingFailure => Left(decodingFailure.getMessage()),
          result => Right(result.copy(userId = userID))
        )
      )
}
