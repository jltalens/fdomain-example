package org.jtalens.fdomain.model

case class AccountWithPoints(userID: UserID, status: AccountStatus.Value, points: Long)
