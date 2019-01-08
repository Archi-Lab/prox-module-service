#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven "apache-maven-3.6.0"
    }
    environment {
        PROJECTNAME = "module-service"
    }
    stages {
        stage("Checkout") {
            steps {
                checkout([
                        $class: "GitSCM",
                        branches: [[name: "*/master"]],
                        credentialsId: "",
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        submoduleCfg: [],
                        userRemoteConfigs: [[credentialsId: '632cd510-b0ca-4567-8f61-5e134a8cff98', url: 'https://fsygs15.gm.fh-koeln.de:8888/PTB/module-service.git']]
                ])
            }
        }
        stage("Build") {
            steps {
                sh "mvn clean package" // Führt den Maven build aus
            }
        }
        stage("Test") {
            steps {
                echo "Testing...."
            }
        }
        stage("Code Quality Check") {
            steps {
                sh "mvn checkstyle:checkstyle"
                //jacoco()
                script { scannerHome = tool "SonarQube Scanner"; }
                withSonarQubeEnv("SonarQube-Server") { sh "${scannerHome}/bin/sonar-scanner" }
            }
            post {
                always {
                    step([$class: "hudson.plugins.checkstyle.CheckStylePublisher", pattern: "**/target/checkstyle-result.xml", unstableTotalAll: "100"])
                }
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying..."
            }
        }
    }
}