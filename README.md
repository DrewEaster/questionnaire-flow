# Questionnaire Flow

## Executing the application

To run the application, you'll need to have SBT installed. Assuming you've installed SBT, execute:

```
sbt run
```

This executes the main class: `com.dreweaster.questionnaire.Main`

## Notes on implementation

I decided to apply some artistic licence to stray slightly from the parts of the specification that I felt were a little ambiguous. The result is what I believe to be a comprehensible implementation of a typical questionnaire flow.

### Divergences from spec

- It does request user input from the console for every question
- It extracts input in real-time from StdIn rather than supporting the passing in of parameters. I found it slightly conflicting that the spec mentions both passing a "parameter" and "entering" answers. Thus I chose to focus on allowing a user to enter answers step-by-step from the console.
- At the end of the flow, the answers the user entered are written to the console

### General implementation notes

I broke up the problem into three distinct concerns:

- Definition of questionnaire steps using a simple ubiquitous language that models the domain clearly
- Declarative definition of how the steps are combined to form a flow (questionnaire)
- Execution of the questionnaire using a `QuestionnaireRunner` - for this exercise, I created a `ConsoleRunner` implementation to extract answers from the console.

I felt it was especially important to separate the declaration of a questionnaire from its execution. This allows a questionnaire to be executed in a number of different ways, not just via the console.

For time reasons, I chose not to pursue any proper error handling (e.g. if user enters their age as a non-numerical string, the application will just exit immediately with an Exception)

I've written a feature-oriented test suite (`com.dreweaster.questionnaire.runner.console.ConsoleRunnerTest`) that runs two scenarios to test the branching feature of a decision step. This fakes a `Console` by allowing the test to simulate the user inputs.