package org.jtalens.fdomain.services.io

import org.jtalens.fdomain.model.{ AccountStatus, AccountSummary, UserID }
import org.specs2.Specification

class AccountSummaryParsersSpec extends Specification {
  def is = s2"""
  Given a valid json String must return a valid AccountSummary $accountSummaryFromJsonString
  Must catch the error if the json is invalid $invalidJson
  """

  val accountSummaryParser = new AccountSummaryParsers {}

  def accountSummaryFromJsonString = {
    val json =
      """
        |{
        |  "userId": {
        |    "id": "user 123"
        |  },
        |  "status": "WaitingForConfirmation",
        |  "externalAccountId": "321"
        |}
      """.stripMargin
    val actual = accountSummaryParser.fromString(json)(UserID("asf"))
    actual must beRight(AccountSummary(UserID("asf"), AccountStatus.WaitingForConfirmation, None, Some(321)))
  }

  def invalidJson = {
    val json =
      """
        |{
        |  "userId": {
        |    "id": "user 123"
        |  },
        |  "status": "WaitingForConfirmation",
        |  "externalAccountId": "321"
        |
      """.stripMargin
    val actual = accountSummaryParser.fromString(json)(UserID("asfd"))
    actual must beLeft[String]
  }
}
