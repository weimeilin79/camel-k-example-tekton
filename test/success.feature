Feature: integration runs

  Background:
    Given URL: http://prescription/

  Scenario: Given integration prescription is running
    Given print 'Then integration hello should print Hello Camel K from Tekton pipelines'

  
  Scenario: Get PUT Method
    When send PUT /prescription
    Given HTTP request headers
      | Content-Type          | application/json;charset=UTF-8 |
    Given HTTP request body: {"profileid": 123456, "patient": "Me", "complaint": "Cold Symptoms", "diagnosis": "Acute Bronchitis", "prescription": [ { "medicine": "benzonatate", "dosage": "3 times per day" }, { "medicine": "ProAir HFA", "dosage": "every 4 to 6 hours" } ], "pharmancy": "CVS" }
    Then receive HTTP 415 OK
    
    