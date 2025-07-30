pipelineJob('my-pipeline-job') {
    definition {
        cps {
            script("""
                pipeline {
                    agent any
                    stages {
                        stage('Hello') {
                            steps {
                                echo 'Hello from GitOps!'
                            }
                        }
                    }
                }
            """)
            sandbox(true)
        }
    }
}
