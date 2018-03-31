package org.jtalens.fdomain.router
import cats.effect._
import cats.free.Free
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.jtalens.fdomain.model.{AccountStatus, AccountSummary, UserID}
import org.jtalens.fdomain.repository.AccountSummaryAlg._
import org.jtalens.fdomain.repository.FreeF
import org.jtalens.fdomain.repository.interpreter.AccountSummaryIntIO



object AccountRouter  {
  import AccountRouterEncoders._
  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  val comp: FreeF[Either[String, Option[AccountSummary]]] = findById(UserID("something"))
    .flatMap {
      case None => Free.pure(Left("Coudn't find the user"))
      case Some(accountSummary) => save(accountSummary.copy(status = AccountStatus.Created)).map(Right(_))
    }

  val httpRoutes: HttpService[IO] =
    HttpService[IO] {
      case GET -> Root / "api" / "domain-example" / userId =>
        for {
          accountSummary <- comp.foldMap(AccountSummaryIntIO.compiler)
          resp <- accountSummary match {
            case Left(error) => BadRequest(error)
            case Right(summary) => Ok(summary)
          }
        } yield resp
  }
}

object AccountRouterEncoders {
  implicit def accountSummaryEncoder: EntityEncoder[IO, Option[AccountSummary]] = jsonEncoderOf[IO, Option[AccountSummary]]
}
