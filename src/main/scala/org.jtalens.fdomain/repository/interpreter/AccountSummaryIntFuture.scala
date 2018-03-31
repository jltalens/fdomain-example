package org.jtalens.fdomain.repository.interpreter

import java.time.LocalDateTime

import cats.effect.IO
import cats.{Id, ~>}
import io.circe.parser.parse
import monix.eval.Task
import org.jtalens.fdomain.model.{AccountStatus, AccountSummary, UserID}
import org.jtalens.fdomain.repository._

import scala.concurrent.Future
import scala.io.Source
import scala.util.Try

trait Interpreter[F[_]] {
  def compiler: AccountSummaryAlgF ~> F
}

object Samples {
  val accountSummary = AccountSummary(
    userId = UserID("userId"),
    status = AccountStatus.WaitingForConfirmation,
    timestamp = None,
    externalAccountId = None
  )
}

object AccountSummaryIntFuture extends Interpreter[Future] {
  implicit val ex = scala.concurrent.ExecutionContext.global

  def compiler: AccountSummaryAlgF ~> Future = new (AccountSummaryAlgF ~> Future) {
    def apply[A](fa: AccountSummaryAlgF[A]): Future[A] = fa match {
      case Save(_) => Future.successful(Some(Samples.accountSummary))
      case FindByStatus(_) => Future.successful(Seq(Samples.accountSummary))
      case FindById(_) => Future.successful(Some(Samples.accountSummary))
    }
  }
}

object AccountSummaryIntTask extends Interpreter[Task] {

  def compiler: ~>[AccountSummaryAlgF, Task] = new (AccountSummaryAlgF ~> Task) {
    def apply[A](fa: AccountSummaryAlgF[A]): Task[A] = fa match {
      case Save(_) => Task.now(Some(Samples.accountSummary))
      case FindByStatus(_) => Task.now(Seq(Samples.accountSummary))
      case FindById(_) => Task.now(Some(Samples.accountSummary))
    }
  }
}

object AccountSummaryIntId extends Interpreter[Id] {
  def compiler: AccountSummaryAlgF ~> Id = new (AccountSummaryAlgF ~> Id) {
    def apply[A](fa: AccountSummaryAlgF[A]): Id[A] = fa match {
      case Save(accountSummary) => Some(accountSummary.copy(userId = UserID("id monad")))
      case FindByStatus(accountStatus) => Seq(AccountSummary(userId = UserID("fake"), status = accountStatus))
      case FindById(id) => Some(AccountSummary(userId = id, status = AccountStatus.Created))
    }
  }
}

object AccountSummaryIntIO extends Interpreter[IO] {
  def compiler: ~>[AccountSummaryAlgF, IO] = new (AccountSummaryAlgF ~> IO) {
    def apply[A](fa: AccountSummaryAlgF[A]): IO[A] = fa match {
      case FindById(_) => IO {
        Try(Source.fromResource("account_summary.json").mkString)
          .toOption
          .flatMap(decodeAccountSummary)
      }
      case Save(accountSummary) => IO {
        Some(accountSummary.copy(timestamp = Some(LocalDateTime.now())))
      }
      case FindByStatus(accountStatus) => IO(Seq.empty)
    }
  }
  private def decodeAccountSummary(content: String): Option[AccountSummary] =
    parse(content)
      .map(json => json.as[AccountSummary])
    .fold(
      _ => None,
      decoderResult => decoderResult.toOption
    )
}
