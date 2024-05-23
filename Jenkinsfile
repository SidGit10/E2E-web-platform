pipeline {
    agent any

    environment {
        BROWSERSTACK_USERNAME = credentials('browserstack_credentials').username
        BROWSERSTACK_ACCESS_KEY = credentials('browserstack_credentials').password
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                // Add your build steps here
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests on BrowserStack...'

                script {
                    browserstack(credentialsId: 'browserstack_credentials') {
                        sh """
                            # Run your test script using BrowserStack credentials
                            # For example, using a Selenium test
                            export BROWSERSTACK_USERNAME=${BROWSERSTACK_USERNAME}
                            export BROWSERSTACK_ACCESS_KEY=${BROWSERSTACK_ACCESS_KEY}
                            cd test
                            ln src/test/resources/conf/capabilities/${TEST_TYPE}.yml browserstack.yml
                            mvn clean test -P ${TEST_TYPE} '''
                        """
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying...'
                // Add your deployment steps here
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
