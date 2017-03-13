package com.dreweaster.questionnaire.runner.console

import com.dreweaster.questionnaire.flow.QuestionnaireFlowBuilder.{EndBlock, DecisionBlock, QuestionBlock, QuestionnaireBlock}
import com.dreweaster.questionnaire.model.{No, Yes, QuestionId, Question}
import com.dreweaster.questionnaire.runner.QuestionnaireRunner

trait Console {
  def write(text: String): Unit

  def read(): String
}

object RealConsole extends Console {
  override def write(text: String) = print(text)

  override def read() = scala.io.StdIn.readLine()
}

class ConsoleRunner(console: Console) extends QuestionnaireRunner {
  def run(questionnaire: QuestionnaireBlock, previousAnswers: Map[QuestionId, String]): Map[QuestionId, String] = {
    questionnaire match {
      case QuestionBlock(question, next) =>
        val answer = collectAnswer(question)
        run(next, previousAnswers + (question.id -> answer))
      case DecisionBlock(decisionMapping, yes, no) =>
        decisionMapping.decision.rule(previousAnswers(decisionMapping.applyToAnswerFrom.id)) match {
          case Right(Yes) => run(yes, previousAnswers)
          case Right(No) => run(no, previousAnswers)
          case Left(ex) => throw ex
        }
      case EndBlock => previousAnswers
    }
  }

  private def collectAnswer(question: Question) = {
    console.write(s"(${question.answerType.typeString}) ${question.questionText}: ")
    console.read()
  }
}

object RealConsoleRunner extends ConsoleRunner(RealConsole)