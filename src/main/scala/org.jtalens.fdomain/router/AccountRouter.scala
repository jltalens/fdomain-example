package org.jtalens.fdomain.router

import cats.effect._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.jtalens.fdomain.model.{AccountSummary, UserID}
import org.jtalens.fdomain.repository.AccountSummaryAlg._
import org.jtalens.fdomain.repository.interpreter.AccountSummaryIntIO
import org.jtalens.fdomain.services.io.AccountSummaryServiceFile


object AccountRouter {

  import AccountRouterEncoders._

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  val httpRoutes: HttpService[IO] =
    HttpService[IO] {
      case GET -> Root / "api" / "domain-example" / userId =>
        for {
          accountSummary <-
            findById(UserID(userId))
              .foldMap(AccountSummaryIntIO.compiler(AccountSummaryServiceFile))
          resp <- accountSummary match {
            case Left(error) => BadRequest(error)
            case Right(summary) => Ok(summary)
          }
        } yield resp
    }
}

object AccountRouterEncoders {
  implicit def accountSummaryEncoder: EntityEncoder[IO, AccountSummary] = jsonEncoderOf[IO, AccountSummary]
}
