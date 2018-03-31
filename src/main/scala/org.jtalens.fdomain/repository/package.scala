package org.jtalens.fdomain
import cats.free.Free

package object repository {
  type FreeF[F] = Free[AccountSummaryAlgF, F]
}
