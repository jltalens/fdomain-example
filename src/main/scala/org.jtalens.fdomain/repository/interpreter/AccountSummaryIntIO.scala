package org.jtalens.fdomain.repository.interpreter

import java.time.LocalDateTime

import cats.effect.IO
import cats.~>
import io.circe.parser.parse
import org.jtalens.fdomain.model.AccountSummary
import org.jtalens.fdomain.repository._
import org.jtalens.fdomain.services.AccountSummaryService

trait Interpreter[F[_]] {
  def compiler(accountSummaryService: AccountSummaryService): AccountSummaryAlgF ~> F
}

object AccountSummaryIntIO extends Interpreter[IO] {
  def compiler(accountSummaryService: AccountSummaryService): ~>[AccountSummaryAlgF, IO] = new (AccountSummaryAlgF ~> IO) {
    def apply[A](fa: AccountSummaryAlgF[A]): IO[A] = fa match {
      case FindById(id) =>
        accountSummaryService.accountSummary(id)
      case Save(accountSummary) => IO {
        Right(accountSummary.copy(timestamp = Some(LocalDateTime.now())))
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
