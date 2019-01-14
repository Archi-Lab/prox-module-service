#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven "apache-maven-3.6.0"
        jdk "JDK_8u191"
    }
    environment {
        PROJECTNAME = "module-service"
    }
    stages {
        stage("Build") {
            steps {
                updateGitlabCommitStatus name: "Packaging", state: "running"
                sh "mvn clean package -Dmaven.test.skip=true" // Führt den Maven build aus
                sh "docker build -t repository.archi-lab.io/ptb-module-service ." // baut die Java App auf dem Container
                post {
                    success {
                        updateGitlabCommitStatus name: "Building", state: "success"
                    }
                    failure {
                        updateGitlabCommitStatus name: "Building", state: "failed"
                    }
                    unstable {
                        updateGitlabCommitStatus name: "Building", state: "success"
                    }
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    // requires SonarQube Scanner 3.2+
                    scannerHome = tool 'TH Koeln GM SonarQube Scanner'
                }
                withSonarQubeEnv('TH Koeln GM SonarQube') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        stage("Test") {
            steps {
                echo "Testing..."
            }
        }
        stage("Code Quality Check") {
            steps {
                echo "Code Quality Check..."
            }
        }
        stage("Deploy") {
            steps {
                sh "docker network inspect ptb-backend &> /dev/null || docker network create ptb-backend"
                sh "docker network inspect module-service_db &> /dev/null || docker network create module-service_db"
                sh "docker-compose -p ptb up -d"
            }
            post {
                success {
                    updateGitlabCommitStatus name: "Deploying", state: "success"
                }
                failure {
                    updateGitlabCommitStatus name: "Deploying", state: "failed"
                }
                unstable {
                    updateGitlabCommitStatus name: "Deploying", state: "success"
                }
            }
        }
    }
}
