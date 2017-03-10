package com.dreweaster.questionnaire.model

sealed trait DecisionResult

object Yes extends DecisionResult

object No extends DecisionResult

case class Decision(rule: String => Either[Throwable, DecisionResult]) {
  def run(answer: String) = rule(answer)
}
