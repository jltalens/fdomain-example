package org.jtalens.fdomain.services.io

import io.circe.parser._
import org.jtalens.fdomain.model.{ AccountSummary, UserID }

trait AccountSummaryParsers {
  def fromString(accountSummaryStr: String)(userID: UserID): Either[String, AccountSummary] = parse(accountSummaryStr)
    .map(json => json.as[AccountSummary])
    .fold(
      parsingFailure => Left(parsingFailure.message),
      decoderResult => decoderResult.fold(
        decodingFailure => Left(decodingFailure.getMessage()),
        result => Right(result.copy(userId = userID))))
}