def call(String imageName) {
    echo " Building Docker image ${imageName}:${env.BUILD_NUMBER}"

    sh """
    docker build -t ${imageName}:${env.BUILD_NUMBER} .
    docker push ${imageName}:${env.BUILD_NUMBER}
    """
}
stage('Docker Login') {
    steps {
        withCredentials([string(credentialsId: 'dockerhub-pass', variable: 'DOCKERHUB_PASS')]) {
            sh "echo $DOCKERHUB_PASS | docker login -u mohamedeid --password-stdin"
        }
    }
}