Feature: integration runs

  Scenario:
    Given integration hello is running
    Then integration hello should print Hello Camel K from Tekton pipelines
