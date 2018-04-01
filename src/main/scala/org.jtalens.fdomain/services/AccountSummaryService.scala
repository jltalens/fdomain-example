package org.jtalens.fdomain.services

import cats.effect.IO
import org.jtalens.fdomain.model.{ AccountSummary, UserID }

trait AccountSummaryService {
  def accountSummary(userId: UserID): IO[Either[String, AccountSummary]]
}

