# Camel K Pipeline Example
 
![Camel K CI](https://github.com/openshift-integration/camel-k-example-basic/workflows/Camel%20K%20CI/badge.svg)

This example creates an pipeline that deploys Camel K applications.

Tekton is a powerful yet flexible Kubernetes-native open-source framework for creating continuous integration and delivery (CI/CD) systems. It lets you build, test, and deploy across multiple cloud providers or on-premises systems by abstracting away the underlying implementation details.

Camel K can be directly used in Tekton Pipelines tasks since it’s container image ships the kamel CLI tool, that can be used to create all needed resources by interacting with the Kubernetes cluster.

## Before you begin

Make sure you check-out this repository from git and open it with [VSCode](https://code.visualstudio.com/).

Instructions are based on [VSCode Didact](https://github.com/redhat-developer/vscode-didact), so make sure it's installed
from the VSCode extensions marketplace.

From the VSCode UI, click on the `readme.didact.md` file and select "Didact: Start Didact tutorial from File". A new Didact tab will be opened in VS Code.

[Make sure you've checked all the requirements](./requirements.didact.md) before jumping into the tutorial section.

## Checking requirements

<a href='didact://?commandId=vscode.didact.validateAllRequirements' title='Validate all requirements!'><button>Validate all Requirements at Once!</button></a>


**OpenShift CLI ("oc")**

The OpenShift CLI tool ("oc") will be used to interact with the OpenShift cluster.

[Check if the OpenShift CLI ("oc") is installed](didact://?commandId=vscode.didact.cliCommandSuccessful&text=oc-requirements-status$$oc%20help "Tests to see if `oc help` returns a 0 return code"){.didact}

*Status: unknown*{#oc-requirements-status}

**Tekton CLI ("tkn")**

The Tekton CLI tool ("tkn") will be used to interact with Tekton.

[Check if the Tekton CLI ("tkn") is installed](didact://?commandId=vscode.didact.cliCommandSuccessful&text=tkn-requirements-status$$tkn%20help "Tests to see if `tkn help` returns a 0 return code"){.didact}

*Status: unknown*{#tkn-requirements-status}

**Apache Camel K CLI ("kamel")**

Apart from the support provided by the VS Code extension, you also need the Apache Camel K CLI ("kamel") in order to 
access all Camel K features.

[Check if the Apache Camel K CLI ("kamel") is installed](didact://?commandId=vscode.didact.requirementCheck&text=kamel-requirements-status$$kamel%20version$$Camel%20K%20Client&completion=Apache%20Camel%20K%20CLI%20is%20available%20on%20this%20system. "Tests to see if `kamel version` returns a result"){.didact}

*Status: unknown*{#kamel-requirements-status}


**Connection to an OpenShift cluster**

You need to connect to an OpenShift cluster in order to run the examples.

[Check if you're connected to an OpenShift cluster](didact://?commandId=vscode.didact.requirementCheck&text=cluster-requirements-status$$oc%20get%20project$$NAME&completion=OpenShift%20is%20connected. "Tests to see if `kamel version` returns a result"){.didact}

*Status: unknown*{#cluster-requirements-status}




## 1. Preparing a new OpenShift project

Go to your working project `userX-new` where you'll run the Pipeline.

To create the project, open a terminal tab and type the following command:


```
oc project userX-new
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$oc%20project%20userX-new&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})


**OpenShift Pipelines Operator** should have installed. 

Note: 
**Setting up a Service Account**
There are some premission that needs to pre-set before started as cluster admin, this is already pre-installed for you.

```
oc apply -f camel-k-pipeline-permissions.yaml
```


## 2. Creating the Pipeline Definition

This creates a series of resources, including a pipeline definition that you can immediately see on the OpenShift developer console.

```
oc apply -f camel-k-pipeline-task-definition.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$oc%20apply%20-f%20camel-k-pipeline-task-definition.yaml&completion=pipeline%20configured. "Opens a new terminal and sends the command above"){.didact})

The pipeline just created is composed of three tasks.

First task just installs the operator in the namespace, second task, starts an Camel K application and the last task starts an Camel K in Quarkus runtime.

The tasks are executed in sequence in the pipeline from installing the operator and runs both Camel K applications.

You can list all the avaliable tasks with the following command.

```
tkn task list
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$tkn%20task%20list. "Opens a new terminal and sends the command above"){.didact})

You shoule see three tasks listed:

    - camel-k-install-operator   
    - camel-k-run-integration    
    - camel-k-run-quarkus        


## 3. Triggering a Pipeline execution

Everything is now ready to be executed and the last thing missing is a trigger. First you can list all pipelines.

```
tkn pipeline list
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$tkn%20pipeline%20list. "Opens a new terminal and sends the command above"){.didact})

You will see a pipeline listed below

    - camel-k-pipeline

Starts the execution of the pipeline and its progress can be monitored on the OpenShift console.

```
tkn pipeline start camel-k-pipeline
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$tkn%20pipeline%20start%20camel-k-pipeline. "Opens a new terminal and sends the command above"){.didact})

The result of the pipeline execution is integration named hello and basic running on the cluster.


Congrats! You have complete this example. To re-run, make sure you reset the namespace.

```
kamel reset

oc delete integrationplatform camel-k
```