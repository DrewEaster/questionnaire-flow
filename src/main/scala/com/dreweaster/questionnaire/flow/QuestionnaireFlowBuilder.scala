package com.dreweaster.questionnaire.flow

import com.dreweaster.questionnaire.flow.QuestionnaireFlowBuilder.{DecisionBlock, EndBlock, QuestionBlock, QuestionnaireBlock}
import com.dreweaster.questionnaire.model.{Decision, Question}

object QuestionnaireFlowBuilder {

  sealed trait QuestionnaireBlock

  object EndBlock extends QuestionnaireBlock

  case class QuestionBlock(question: Question, next: QuestionnaireBlock) extends QuestionnaireBlock

  case class DecisionBlock(decision: Decision, yes: QuestionnaireBlock, no: QuestionnaireBlock) extends QuestionnaireBlock
}

trait QuestionnaireFlowBuilder {

  def question(q: Question)(implicit next: QuestionnaireBlock = EndBlock): QuestionnaireBlock = QuestionBlock(q, next)

  def decision(d: Decision)(yes: QuestionnaireBlock, no: QuestionnaireBlock): QuestionnaireBlock = DecisionBlock(d, yes, no)
}
