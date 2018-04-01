package org.jtalens.fdomain.services.io

import org.jtalens.fdomain.model.{ AccountStatus, AccountSummary, UserID }
import org.specs2.Specification

class AccountSummaryServiceFileSpec extends Specification {

  def is = s2"""
  Loading an AccountSummary from a file:
  - will return the AccountSummary model $returnAccountSummaryModel
  """

  def returnAccountSummaryModel = {
    val userId = UserID(id = "132")
    val actual = AccountSummaryServiceFile.accountSummary(userId).unsafeRunSync()
    val expected = AccountSummary(userId, AccountStatus.WaitingForConfirmation, None, Some(321))
    actual must beRight(expected)
  }

}
