pipeline {
    agent any

    environment {
        // Set your GitHub repository URL
        REPO_URL = 'https://github.com/kirancofc/empsvc.git'
        AWS_ACCOUNT_ID = '851725315615'
        AWS_REGION = 'us-east-2' // Set your desired region
        ECR_REPO = 'employee_service_repo'
        REPOSITORY_URI = '851725315615.dkr.ecr.us-east-2.amazonaws.com'
        IMAGE_REPO_NAME = 'student-management-system'
        IMAGE_TAG = "${BUILD_NUMBER}"
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
                //sh 'mvn clean package'
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
        
        stage('Build Docker image and Push to ECR') {
            environment {
               REGISTRY_CREDENTIALS = credentials('AWS')
               DOCKER_IMAGE = ${IMAGE_REPO_NAME}:${BUILD_NUMBER}
            }
            steps {
                script{
                    sh 'docker build -t ${DOCKER_IMAGE} .'
                    def dockerImage = docker.image("${DOCKER_IMAGE}")
                    docker.withRegistry("${REPOSITORY_URI}", "AWS") {
                    dockerImage.push()
                }
                 
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
