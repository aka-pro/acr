FROM jenkins/jenkins:lts-jdk11
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
#ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml
EXPOSE 8080
RUN jenkins-plugin-cli --plugins build-timeout:latest email-ext:latest git:latest github-branch-source:latest mailer:latest pipeline-github-lib:latest
RUN jenkins-plugin-cli --plugins pipeline-stage-view:latest ssh-slaves:latest timestamper:latest workflow-aggregator:latest ws-cleanup:latest azure-ad:latest
RUN jenkins-plugin-cli --plugins configuration-as-code
COPY casc.yaml /var/jenkins_home/casc.yaml