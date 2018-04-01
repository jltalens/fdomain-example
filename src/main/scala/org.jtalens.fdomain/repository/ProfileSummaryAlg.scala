package org.jtalens.fdomain.repository

import cats.free.Free
import org.jtalens.fdomain.model.ProfileSummary

sealed trait ProfileSummaryAlgF[A]

case class Fetch(externalId: String) extends ProfileSummaryAlgF[Either[String, ProfileSummary]]
case class Store(profileSummary: ProfileSummary) extends ProfileSummaryAlgF[Boolean]

trait ProfileSummaryAlg {
  def fetch(externalId: String): FreeProfileSummary[Either[String, ProfileSummary]] =
    Free.liftF(Fetch(externalId))

  def store(profileSummary: ProfileSummary): FreeProfileSummary[Boolean] =
    fetch(profileSummary.id).flatMap {
      case Right(_) => Free.liftF(Store(profileSummary))
      case Left(_)  => Free.pure(false)
    }
}

object ProfileSummaryAlg extends ProfileSummaryAlg