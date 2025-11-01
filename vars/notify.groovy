def call(String status) {
    if (status == 'SUCCESS') {
        echo " Build #${env.BUILD_NUMBER} succeeded!"
    } else {
        echo " Build #${env.BUILD_NUMBER} failed!"
    }
}
