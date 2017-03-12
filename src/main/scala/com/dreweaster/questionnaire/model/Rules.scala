package com.dreweaster.questionnaire.model

import scala.util.{Failure, Success, Try}

object Rules {

  def greaterThanOrEqualTo(value: Int): String => Either[Throwable, DecisionResult] = answer => Try(answer.toInt) match {
    case Success(age) if age >= value => Right(Yes)
    case Success(age) => Right(No)
    case Failure(ex) => Left(ex)
  }
}
