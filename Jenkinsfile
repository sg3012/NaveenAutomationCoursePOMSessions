pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Build App updated'
            }
        }
		
		stage('Test') {
            steps {
                echo 'Test App'
            }
        }
		
		stage('Deploy') {
            steps {
                echo 'Deploy App'
            }
        }
    }
	post{
	
		always{
            emailext attachLog: true, body: 'This is the status of the latest run of the pipeline-SamplePipeline', 
            subject: 'Sample Pipeline Results', to: 'sgnight30@gmail.com'
		}
	}
}
