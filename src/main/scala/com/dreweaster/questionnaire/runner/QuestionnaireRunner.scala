package com.dreweaster.questionnaire.runner

import com.dreweaster.questionnaire.flow.QuestionnaireFlowBuilder.{EndBlock, DecisionBlock, QuestionBlock, QuestionnaireBlock}
import com.dreweaster.questionnaire.model.{No, Yes, QuestionId, Question}

trait QuestionnaireRunner {
  def run(questionnaire: QuestionnaireBlock, previousQuestion: Option[Question] = None, previousAnswer: Option[String] = None): Map[QuestionId, String]
}