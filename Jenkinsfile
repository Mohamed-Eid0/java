pipeline {
    agent any

    tools {
        maven 'Maven-3.5.2'  
    }

    environment {
        IMAGE_NAME = "mohamedeid/java-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Hassan-Eid-Hassan/python-iti.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo " Build Number: ${currentBuild.number}"
                    if (currentBuild.number.toInteger() < 1) {
                        error(" Build number is less than 1. Stopping pipeline!")
                    }
                }
                sh "mvn clean package -DskipTests"
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-pass', variable: 'DOCKERHUB_PASS')]) {
                    sh "echo $DOCKERHUB_PASS | docker login -u mohamedeid --password-stdin"
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh "docker push ${IMAGE_NAME}:${BUILD_NUMBER}"
            }
        }

        stage('Deploy') {
            steps {
                sh """
                echo " Deploying container..."

                docker stop java-app || true
                docker rm java-app || true

                docker run -d -p 9000:8080 --name java-app ${IMAGE_NAME}:${BUILD_NUMBER}

                echo " App running at: http://localhost:9000"
                """
            }
        }
    }

    post {
        success {
            echo " Build Successful!"
        }
        failure {
            echo " Build Failed!"
        }
        always {
            echo " Cleaning workspace..."
            cleanWs()
        }
    }
}
