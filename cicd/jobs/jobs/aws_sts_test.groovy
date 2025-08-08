pipelineJob("aws-sts-test") {
    description("Test AWS STS with credentials from AWS Secrets Manager")
    keepDependencies(false)
    definition {
        cps {
            script("""
                node {
                  docker.image('amazon/aws-cli:2.15.20').inside {
                    withCredentials([string(credentialsId: 'aws-temp-creds', variable: 'AWS_CREDS_JSON')]) {
                      def creds = readJSON text: AWS_CREDS_JSON
                      withEnv([
                        "AWS_ACCESS_KEY_ID=\${creds.AWS_ACCESS_KEY_ID}",
                        "AWS_SECRET_ACCESS_KEY=\${creds.AWS_SECRET_ACCESS_KEY}",
                        "AWS_SESSION_TOKEN=\${creds.AWS_SESSION_TOKEN}",
                        "AWS_REGION=us-west-2",
                        "AWS_PAGER="
                      ]) {
                        sh 'aws sts get-caller-identity'
                      }
                    }
                  }
                }
            """.stripIndent())
            sandbox(true)
        }
    }
    disabled(false)
}
