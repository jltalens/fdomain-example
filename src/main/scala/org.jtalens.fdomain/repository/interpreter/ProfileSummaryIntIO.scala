package org.jtalens.fdomain.repository.interpreter

import cats.effect.IO
import cats.~>
import org.jtalens.fdomain.repository.{ Fetch, ProfileSummaryAlgF }

trait InterpreterB[F[_]] {
  def compiler: ProfileSummaryAlgF ~> F
}

class ProfileSummaryIntIO extends InterpreterB[IO] {
  def compiler: ~>[ProfileSummaryAlgF, IO] = new (ProfileSummaryAlgF ~> IO) {
    def apply[A](fa: ProfileSummaryAlgF[A]): IO[A] = ???
  }
}
