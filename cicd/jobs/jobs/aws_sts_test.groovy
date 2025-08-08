pipelineJob("aws-sts-test") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
"""
        node {
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
        }"""		}
	}
	disabled(false)
}
