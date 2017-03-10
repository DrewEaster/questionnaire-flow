package com.dreweaster.questionnaire.runner.console

import com.dreweaster.questionnaire.Main._
import com.dreweaster.questionnaire.model._
import org.scalatest._

import scala.util.{Failure, Success, Try}

class ConsoleRunnerTest extends FeatureSpec with GivenWhenThen with Matchers {

  info("As a questionnaire creator")
  info("I want to be able to build decision based questionnaire flows")
  info("So I can collect useful data from my customers")

  feature("Decision based flow") {
    scenario("Follow yes branch") {

      Given("a questionnaire flow with a decision step")
      val questionnaire = question(question1) {
        question(question2) {
          decision(isOver18)(
            yes = question(question4),
            no = question(question3)
          )
        }
      }

      When("the user enters an answer that should trigger the yes branch of the decision step")
      val testInputs = new FakeConsole(List("Andrew", "18", "Yes"))
      val answers = new ConsoleRunner(testInputs).run(questionnaire)

      Then("the questionnaire flow should follow the no branch")
      answers.get(question1.id) should be (Some("Andrew"))
      answers.get(question2.id) should be (Some("18"))
      answers.get(question4.id) should be (Some("Yes"))
    }

    scenario("Follow no branch") {

      Given("a questionnaire flow with a decision step")
      val questionnaire = question(question1) {
        question(question2) {
          decision(isOver18)(
            yes = question(question4),
            no = question(question3)
          )
        }
      }

      When("the user enters an answer that should trigger the no branch of the decision step")
      val testInputs = new FakeConsole(List("Andrew", "17", "Teddy"))
      val answers = new ConsoleRunner(testInputs).run(questionnaire)

      Then("the questionnaire flow should follow the no branch")
      answers.get(question1.id) should be (Some("Andrew"))
      answers.get(question2.id) should be (Some("17"))
      answers.get(question3.id) should be (Some("Teddy"))
    }
  }

  val question1 = Question(QuestionId(1), "What's your name?", FreeTextType)
  val question2 = Question(QuestionId(2), "How old are you?", FreeTextType)
  val question3 = Question(QuestionId(3), "Enter your pet please", FreeTextType)
  val question4 = Question(QuestionId(4), "Do you have a driving license?", BooleanType)
  val isOver18 = Decision { answer =>
    Try(answer.toInt) match {
      case Success(age) if age >= 18 => Right(Yes)
      case Success(age) => Right(No)
      case Failure(ex) => Left(ex)
    }
  }

  class FakeConsole(var inputs: Seq[String]) extends Console {
    override def write(text: String) = {
      // Don't need to do anything in this fake implementation
    }

    override def read(): String = {
      val input = inputs.head
      inputs = inputs.tail
      input
    }
  }
}
