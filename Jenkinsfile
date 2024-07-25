pipeline {
    agent any

    environment {
        // Set your GitHub repository URL
        REPO_URL = 'https://github.com/kirancofc/empsvc.git'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Build') {
            steps {
                // Clean and build the project using Maven
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                // Run tests using Maven
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Package the application
                sh 'mvn package'
            }
        }
        
        stage('Deploy') {
            steps {
                // Deploy steps can be added here, such as copying files to a server
                echo 'Deploy stage (optional)'
            }
        }
    }

    post {
        always {
            // Archive the build results
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            
            // Clean workspace
            cleanWs()
        }
        
        success {
            // Send notification for successful build (e.g., email, Slack)
            echo 'Build succeeded!'
        }
        
        failure {
            // Send notification for failed build (e.g., email, Slack)
            echo 'Build failed!'
        }
    }
}
