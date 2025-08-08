pipelineJob("aws-sts-test") {
	description("Test AWS STS with credentials from AWS Secrets Manager using awscli podTemplate")
	keepDependencies(false)
	definition {
		cps {
"""
    pipeline {
        agent {
            label 'awscli'
        }

        stages {
            stage('AWS STS Test') {
                steps {
                    container('aws') {
                        withCredentials([string(credentialsId: 'aws-temp-creds', variable: 'AWS_CREDS_JSON')]) {
                            script {
                                def creds = readJSON text: AWS_CREDS_JSON
                                withEnv([
                                    "AWS_ACCESS_KEY_ID=\${creds.AWS_ACCESS_KEY_ID}",
                                    "AWS_SECRET_ACCESS_KEY=\${creds.AWS_SECRET_ACCESS_KEY}",
                                    "AWS_SESSION_TOKEN=\${creds.AWS_SESSION_TOKEN}",
                                    "AWS_PAGER="
                                ]) {
                                    sh 'aws sts get-caller-identity'
                                }
                            }
                        }
                    }
                }
            }
        }
    }"""		}
	}
	disabled(false)
}
