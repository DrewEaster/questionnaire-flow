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
  def run(questionnaire: QuestionnaireBlock, previousAnswer: Option[String] = None) = {
    val answers = Map[QuestionId, String]()
    questionnaire match {
      case QuestionBlock(question, next) =>
        val answer = collectAnswer(question)
        answers + (question.id -> answer) ++ run(next, Some(answer))
      case DecisionBlock(decision, yes, no) =>
        decision.run(previousAnswer.get) match {
          case Right(Yes) => answers ++ run(yes, previousAnswer)
          case Right(No) => answers ++ run(no, previousAnswer)
          case Left(ex) => throw ex
        }
      case EndBlock => answers
    }
  }

  private def collectAnswer(question: Question) = {
    console.write(s"(${question.answerType.typeString}) ${question.questionText}: ")
    console.read()
  }
}

object RealConsoleRunner extends ConsoleRunner(RealConsole)