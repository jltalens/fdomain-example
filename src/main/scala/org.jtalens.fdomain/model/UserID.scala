package org.jtalens.fdomain.model
import io.circe.generic.semiauto._
import io.circe.syntax._

case class UserID(id: String) extends AnyVal

object UserID {
  implicit val userIDEncoder = deriveEncoder[UserID]
  implicit val userIDDecoder = deriveDecoder[UserID]
}