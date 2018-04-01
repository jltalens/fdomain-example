package org.jtalens.fdomain
import cats.free.Free

package object repository {
  type FreeAccountSummary[F] = Free[AccountSummaryAlgF, F]
  type FreeProfileSummary[F] = Free[ProfileSummaryAlgF, F]
}
