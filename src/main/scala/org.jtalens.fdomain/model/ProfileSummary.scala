package org.jtalens.fdomain.model

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }

case class ProfileSummary(id: String, allowsCommunications: Boolean, score: Long)

object ProfileSummary {
  implicit val encodeAccountSummary: Encoder[ProfileSummary] = deriveEncoder[ProfileSummary]
  implicit val decodeAccountSummary: Decoder[ProfileSummary] = deriveDecoder[ProfileSummary]
}
