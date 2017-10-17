# KubeKleaner

<p align="center">

<img src="https://github.com/att/kubekleaner/blob/master/src/main/resources/k8s.png" height="128" width="128">
<img src="https://github.com/att/kubekleaner/blob/master/src/main/resources/kleanerbottle.png" height="128" width="128">

</p>

## Development Information

Developed for AT&T by Tremaine Eto (https://github.com/tremaineeto), June 2017

Additional collaborator(s)
-----------
George Braxton (https://github.com/gbraxton)

## Introduction

KubeKleaner—short for Kubernetes Kleaner—is a Java application built on the Spring Boot framework and the AT&T Java Service Container (AJSC).

KubeKleaner utilizes Spring Boot's scheduled task functionality to leverage the Kubernetes API for deleting failing microservice deployments and their downstream resources based on a configurable expiration time.

## Cleanup Process

The cleanup process is configurable but by default happens once daily. The Kubernetes clusters that KubeKleaner can reach is configured in the application's properties file.

The cleanup process loops through all pods in the specified cluster(s) and works as follows on each iteration:

1. If pod is in a CrashLoopBackOff state, add current timestamp as a label (named "crashLoopDetectionTime") to its associated deployment resource
2. If timestamp label is on deployment resource and the pod now is in a Running state, remove timestamp label from its associated deployment resource
3. If timestamp label is on deployment resource and is older than the configured expiration and the pod is still in a CrashLoopBackOff state, delete all associated Kubernetes resources (deployment, replica set, pod,  service, ingress, and hpa)

The above strategy is implemented in [CleanupServiceImpl.java](https://github.com/att/kubekleaner/blob/master/src/main/java/com/att/eg/common/platform/kubekleaner/service/CleanupServiceImpl.java).

## Defaults

As configured by default, KubeKleaner runs once daily at 10:00 A.M. PST.

The expiration date for Kubernetes resources is by default set for one week. This can be made shorter or longer via the "numWeeks" environment variable as found in application.yml.

## Notifications

Upon a successful cleanup by KubeKleaner, a HipChat notification (if configured) will be sent to a chosen HipChat room with the following information tied to the deleted resources:

* Service name
* Namespace
* Kubernetes cluster

Below is an example of such a notification.

![alt text](https://github.com/att/kubekleaner/blob/master/src/main/resources/kubekleanerhipchatnotification.PNG "Example KubeKleaner HipChat notification")

## License

KubeKleaner is under the MIT License and is Copyright (c) 2017 AT&T Intellectual Property. All other rights reserved.
