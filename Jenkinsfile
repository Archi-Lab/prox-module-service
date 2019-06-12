pipeline {
    agent any
    tools {
        maven "apache-maven-3.6.0"
        jdk "JDK_8u191"
    }
    environment {
        REPOSITORY = "ptb-gp-ss2019.archi-lab.io"
        IMAGE = "module-service"
    }
    stages {
        stage("Build") {
            steps {
                sh "mvn clean install" // Führt den Maven build aus
                sh "docker image save -o ${IMAGE}.tar ${REPOSITORY}/${IMAGE}" // Docker image als tar Datei speichern
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
            environment {
                SERVERPORT = "22413"
                YMLFILENAME = "docker-compose-module-service.yml"
                SSHUSER = "jenkins"
                SERVERNAME = "fsygs15.inf.fh-koeln.de"
            }
            steps {
                sh "scp -P ${SERVERPORT} -v ${IMAGE}.tar ${SSHUSER}@${SERVERNAME}:~/"     // Kopiert per ssh die tar Datei auf dem Produktionsserver
                sh "scp -P ${SERVERPORT} -v ${YMLFILENAME} ${SSHUSER}@${SERVERNAME}:/srv/projektboerse/"
                sh "ssh -p ${SERVERPORT} ${SSHUSER}@${SERVERNAME} " +
                        "'docker image load -i ${IMAGE}.tar; " +
                        /*"docker network inspect ptb &> /dev/null || docker network create ptb; " + */ // when connecting to other services, enable this
                        "docker-compose -p ptb -f /srv/projektboerse/${YMLFILENAME} up -d'"
            }
        }
    }
}
