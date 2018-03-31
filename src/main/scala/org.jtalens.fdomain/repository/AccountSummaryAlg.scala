package org.jtalens.fdomain.repository

import org.jtalens.fdomain.model._
import cats.free.Free


sealed trait AccountSummaryAlgF[A]

case class Save(accountSummary: AccountSummary) extends AccountSummaryAlgF[Option[AccountSummary]]
case class FindById(userId: UserID) extends AccountSummaryAlgF[Option[AccountSummary]]
case class FindByStatus(status: AccountStatus.Value) extends AccountSummaryAlgF[Iterable[AccountSummary]]

trait AccountSummaryAlg {
  def save(accountSummary: AccountSummary): FreeF[Option[AccountSummary]] =
    Free.liftF(Save(accountSummary))

  def findById(userId: UserID): FreeF[Option[AccountSummary]] =
    Free.liftF(FindById(userId))

  def findByStatus(status: AccountStatus.Value): FreeF[Iterable[AccountSummary]] =
    Free.liftF(FindByStatus(status))
}

object AccountSummaryAlg extends AccountSummaryAlg
