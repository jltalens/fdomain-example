package org.jtalens.fdomain.repository.interpreter

import cats.data.Kleisli
import cats.effect.IO
import cats.~>
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.http4s.{ EntityDecoder, Status, Uri }
import org.jtalens.fdomain.model.ProfileSummary
import org.jtalens.fdomain.repository.{ Fetch, ProfileSummaryAlgF }

trait InterpreterB[F[_]] {
  def compiler(profileSummaryService: ProfileSummaryService, app: AppConfig): ProfileSummaryAlgF ~> F
}

object ProfileSummaryIntIO extends InterpreterB[IO] {
  def compiler(profileSummaryService: ProfileSummaryService, app: AppConfig): ~>[ProfileSummaryAlgF, IO] = new (ProfileSummaryAlgF ~> IO) {
    def apply[A](fa: ProfileSummaryAlgF[A]): IO[A] = fa match {
      case Fetch(id) => profileSummaryService.fetch(id).run(app)
    }
  }
}

class ProfileSummaryService(client: Client[IO]) {

  import ProfileSummaryService._

  def fetch(id: String): Kleisli[IO, AppConfig, Either[String, ProfileSummary]] = Kleisli { config =>
    val target = Uri.fromString(config.profileConfig.getProfileUri).right.get / id
    client.get(target) {
      case Status.Successful(r) => r.attemptAs[ProfileSummary].leftMap(_.message).value
      case r => r.as[String]
        .map(b => Left(s"Request $target failed with status ${r.status.code} and body $b"))
    }
  }

}

case class AppConfig(profileConfig: ProfileConfig)

trait ProfileConfig {
  val getProfileUri: String
}

object ProfileSummaryService {
  implicit def accountSummaryDecoder: EntityDecoder[IO, ProfileSummary] = jsonOf[IO, ProfileSummary]
}