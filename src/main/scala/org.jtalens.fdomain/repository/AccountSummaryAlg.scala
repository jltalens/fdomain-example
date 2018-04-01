package org.jtalens.fdomain.repository

import org.jtalens.fdomain.model._
import cats.free.Free

sealed trait AccountSummaryAlgF[A]

case class Save(accountSummary: AccountSummary) extends AccountSummaryAlgF[Either[String, AccountSummary]]
case class FindById(userId: UserID) extends AccountSummaryAlgF[Either[String, AccountSummary]]
case class FindByStatus(status: AccountStatus.Value) extends AccountSummaryAlgF[Iterable[AccountSummary]]

trait AccountSummaryAlg {
  def save(accountSummary: AccountSummary): FreeAccountSummary[Either[String, AccountSummary]] =
    Free.liftF(Save(accountSummary))

  def findById(userId: UserID): FreeAccountSummary[Either[String, AccountSummary]] =
    Free.liftF(FindById(userId))

  def findByStatus(status: AccountStatus.Value): FreeAccountSummary[Iterable[AccountSummary]] =
    Free.liftF(FindByStatus(status))
}

object AccountSummaryAlg extends AccountSummaryAlg
