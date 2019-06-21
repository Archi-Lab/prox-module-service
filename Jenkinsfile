pipeline {
    agent any

    tools {
        maven "apache-maven-3.6.1"
        jdk "oracle-jdk-8u212"
    }

    environment {
        REPOSITORY  = "docker.nexus.archi-lab.io/archilab"
        IMAGE       = "prox-module-service"
        SERVERNAME  = "fsygs15.inf.fh-koeln.de"
        SERVERPORT  = "22412"
        SSHUSER     = "jenkins"
        YMLFILENAME = "docker-compose-module-service.yml"
    }

    stages {
        stage("Build") {
            steps {
                sh "mvn clean package"
                sh "docker image save -o ${IMAGE}.tar ${REPOSITORY}/${IMAGE}"
            }
        }

        stage("Deploy") {
            steps {
                sh "scp -P ${SERVERPORT} -v ${IMAGE}.tar ${SSHUSER}@${SERVERNAME}:~/"
                sh "scp -P ${SERVERPORT} -v ${YMLFILENAME} ${SSHUSER}@${SERVERNAME}:/srv/prox/"
                sh "ssh -p ${SERVERPORT} ${SSHUSER}@${SERVERNAME} " +
                    "'docker image load -i ${IMAGE}.tar; " +
                    "docker network inspect prox &> /dev/null || docker network create prox; " +
                    "docker-compose -p prox -f /srv/prox/${YMLFILENAME} up -d'"
            }
        }
    }
}
