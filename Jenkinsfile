pipeline {
    agent any

    environment {
        // Set your GitHub repository URL
        REPO_URL = 'https://github.com/kirancofc/empsvc.git'
        AWS_ACCOUNT_ID = '851725315615'
        AWS_REGION = 'us-east-2' 
        ECR_REPO = 'employee_service_repo'
        ECR_URI = '851725315615.dkr.ecr.us-east-2.amazonaws.com/employee_service_repo'
        ECR_REG = 'https://851725315615.dkr.ecr.us-east-2.amazonaws.com'
        ECR_REG_CRED = 'ecr:us-east-2:awsjenkinsuser'
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
                    //sh 'docker build -t ${IMAGE_REPO_NAME}:${BUILD_NUMBER} .'
                    dockerImage = docker.build( ECR_URI +":${BUILD_NUMBER}",".")
                }
            }
        }
       //stage('Logging into AWS ECR') {
            //steps {
                //script {
                //sh """aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"""
                //}
                 
            //}
        //}
        stage('Push Docker Image to ECR') {
            steps{  
                script {
                //sh """docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:$IMAGE_TAG"""
                //sh """docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"""
                    docker.withRegistry(ECR_REG, ECR_REG_CRED) {
                    dockerImage.push("${BUILD_NUMBER}")
                    dockerImage.push('latest')
                    }
                    
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
