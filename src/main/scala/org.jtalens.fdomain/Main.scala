package org.jtalens.fdomain

import cats.effect.IO
import fs2.StreamApp
import org.http4s.server.blaze.BlazeBuilder
import org.jtalens.fdomain.router.AccountRouter

import scala.language.higherKinds
object Main extends StreamApp[IO] {

  implicit val ec = scala.concurrent.ExecutionContext.global

  def stream(args: List[String], requestShutdown: IO[Unit]): fs2.Stream[IO, StreamApp.ExitCode] =
    BlazeBuilder[IO]
      .bindHttp(8888, "localhost")
      .mountService(AccountRouter.httpRoutes, "/")
      .serve
}
