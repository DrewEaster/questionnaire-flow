package com.dreweaster.questionnaire.flow

import com.dreweaster.questionnaire.flow.QuestionnaireFlowBuilder.{DecisionBlock, EndBlock, QuestionBlock, QuestionnaireBlock}
import com.dreweaster.questionnaire.model.{Decision, Question}

object QuestionnaireFlowBuilder {

  sealed trait QuestionnaireBlock

  object EndBlock extends QuestionnaireBlock

  case class QuestionBlock(question: Question, next: QuestionnaireBlock) extends QuestionnaireBlock

  case class DecisionBlock(decisionMapping: DecisionMapping, yes: QuestionnaireBlock, no: QuestionnaireBlock) extends QuestionnaireBlock
}

case class DecisionMapping(decision: Decision, applyToAnswerFrom: Question)

case class DecisionMappingBuilder(decision: Decision) {
  def onAnswerFrom(applyToAnswerFrom: Question) = DecisionMapping(decision, applyToAnswerFrom)
}

trait QuestionnaireFlowBuilder {

  implicit def decisionToDecisionMappingBuilder(decision: Decision): DecisionMappingBuilder = DecisionMappingBuilder(decision)

  def question(q: Question)(implicit next: QuestionnaireBlock = EndBlock): QuestionnaireBlock = QuestionBlock(q, next)

  def decision(d: DecisionMapping)(yes: QuestionnaireBlock, no: QuestionnaireBlock): QuestionnaireBlock = DecisionBlock(d, yes, no)
}
