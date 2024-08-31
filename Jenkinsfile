pipeline {
    agent any

    environment {
        // Set your GitHub repository URL
        REPO_URL = 'https://github.com/kirancofc/empsvc.git'
        AWS_ACCOUNT_ID = '851725315615'
        AWS_REGION = 'us-east-2' 
        ECR_REPO = 'employee_service_repo'
        REPOSITORY_URI = '851725315615.dkr.ecr.us-east-2.amazonaws.com'
        ECR_URI = '851725315615.dkr.ecr.us-east-2.amazonaws.com/employee_service_repo'
        ECR_REG = 'https://851725315615.dkr.ecr.us-east-2.amazonaws.com'
        //ECR_REG_CRED = 'ecr:us-east-2:awsjenkinsuser'
        //ECR_REG_CRED = 'ecr:us-east-2:aws'
        IMAGE_REPO_NAME = 'student-management-system'
        IMAGE_TAG = "${BUILD_NUMBER}"

        cluster = 'SystemSol_Employee_Service'
        service = 'EmpSvcService'
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

                script{
                    dockerImage = docker.build(ECR_URI + ":${BUILD_NUMBER}")
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps{  
                script {
                    //docker.withRegistry(ECR_REG, ECR_REG_CRED) {
                    docker.withRegistry(ECR_REG) {
                    dockerImage.push("${BUILD_NUMBER}")
                    dockerImage.push('latest') }
                }
            }
        }

        stage('Deploy to ECS'){
            steps{
                withAWS(credentials:'awsjenkinsuser', region:'us-east-2'){
                    sh 'aws ecs update-service --cluster ${cluster} --service ${service} --force-new-deployment'
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
