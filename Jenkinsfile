pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Get the code from your SCM (e.g., Git)
                git branch: 'main', url: 'https://github.com/madhumitamohan/access-insight-dashboard.git' // Replace YOUR_REPOSITORY_URL and branch if needed
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }

        stage('Lint') {
            steps {
                sh 'npm run lint:report'
            }
        }

        stage('Publish Lint Report') {
            steps {
                sh '''
                    curl -X POST \
                    -H "Content-Type: application/json" \
                    --data-binary @eslint-report.json \
                    http://localhost:8081/api/lint-reports
                '''
            }
        }

        stage('Test') {
            steps {
                sh 'npm run test'
            }
        }

        stage('Build') {
            steps {
                sh 'npm run build'
            }
        }
    }

    post {
        always {
            // Clean up workspace after build
            cleanWs()
        }
        success {
            echo 'Pipeline finished successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}