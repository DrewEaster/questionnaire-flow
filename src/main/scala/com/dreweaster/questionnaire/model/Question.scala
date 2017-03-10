package com.dreweaster.questionnaire.model

sealed trait AnswerType {
  val typeString: String
}

object FreeTextType extends AnswerType {
  val typeString = "Free text"
}

object BooleanType extends AnswerType {
  val typeString = "Boolean"
}

case class QuestionId(id: Long)

case class Question(id: QuestionId, questionText: String, answerType: AnswerType)