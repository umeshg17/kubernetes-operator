pipeline {
  agent any
  environment {
    AWS_CREDS_JSON = credentials('aws-temp-creds')
    AWS_REGION = 'us-west-2'
    AWS_PAGER = ''
  }
  stages {
    stage('Who am I') {
      steps {
        script {
          def creds = readJSON text: env.AWS_CREDS_JSON
          withEnv([
            "AWS_ACCESS_KEY_ID=${creds.AWS_ACCESS_KEY_ID}",
            "AWS_SECRET_ACCESS_KEY=${creds.AWS_SECRET_ACCESS_KEY}",
            "AWS_SESSION_TOKEN=${creds.AWS_SESSION_TOKEN}"
          ]) {
            sh 'aws sts get-caller-identity'
          }
        }
      }
    }
  }
}
