package com.dreweaster.questionnaire

import com.dreweaster.questionnaire.flow.QuestionnaireFlowBuilder
import com.dreweaster.questionnaire.model.Rules.greaterThanOrEqualTo
import com.dreweaster.questionnaire.model._
import com.dreweaster.questionnaire.runner.console.RealConsoleRunner

object Main extends App with QuestionnaireFlowBuilder {

  // Define the constituent steps of the questionnaire
  val question1 = Question(QuestionId(1), "What's your name?", FreeTextType)
  val question2 = Question(QuestionId(2), "How old are you?", FreeTextType)
  val question3 = Question(QuestionId(3), "Enter your pet please", FreeTextType)
  val question4 = Question(QuestionId(4), "Do you have a driving license?", BooleanType)
  val question5 = Question(QuestionId(5), "What's your nationality?", FreeTextType)
  val isOver18 = Decision(greaterThanOrEqualTo(18))

  // Declaratively connect the constituent steps into a flow using a basic DSL
  val questionnaire =
    question(question1) {
      question(question2) {
        decision(isOver18.onAnswerFrom(question2))(
          yes = question(question4),
          no = question(question3)
        )
      }
    }

  // Use a ConsoleRunner to materialise the questionnaire flow
  val answers = RealConsoleRunner.run(questionnaire).foreach { kv =>
    println(s"${kv._1} = ${kv._2}")
  }
}
