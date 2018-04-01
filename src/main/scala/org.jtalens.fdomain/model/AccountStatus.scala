package org.jtalens.fdomain.model

import io.circe.{ Decoder, Encoder }

object AccountStatus extends Enumeration {
  type AccountStatus = Value
  val Created, Suspended, WaitingForConfirmation = Value

  implicit val accountStatusEncoder = Encoder.enumEncoder(AccountStatus)
  implicit val accountStatusDecoder = Decoder.enumDecoder(AccountStatus)
}