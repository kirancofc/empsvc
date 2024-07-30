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
        
        stage('Build Docker image') {
            steps {
                // Deploy steps can be added here, such as copying files to a server
                //echo 'Deploy stage (optional)'
                script{
                    sh 'docker build -t ${IMAGE_REPO_NAME}:${BUILD_NUMBER} .'
                }
            }
        }
       stage('Logging into AWS ECR') {
            steps {
                script {
                //sh """aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${REPOSITORY_URI}"""
                    sh """aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin 851725315615.dkr.ecr.us-east-2.amazonaws.com"""
                }
                 
            }
        }
        stage('Push Docker Image to ECR') {
            steps{  
                script {
                sh """docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:$IMAGE_TAG"""
                sh """docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"""
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
