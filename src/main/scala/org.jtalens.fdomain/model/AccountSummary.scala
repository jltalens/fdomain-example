package org.jtalens.fdomain.model
import java.time.LocalDateTime

import io.circe._
import io.circe.generic.semiauto._
import io.circe.java8.time._

case class AccountSummary(
  userId:            UserID,
  status:            AccountStatus.Value,
  timestamp:         Option[LocalDateTime] = Some(LocalDateTime.now()),
  externalAccountId: Option[Long]          = None)

object AccountSummary {
  implicit val encodeAccountSummary: Encoder[AccountSummary] = deriveEncoder[AccountSummary]
  implicit val decodeAccountSummary: Decoder[AccountSummary] = deriveDecoder[AccountSummary]
}
