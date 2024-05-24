import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

node {
    environment {
        MAVEN_HOME = tool 'Default'  // Ensure this matches the name configured in Global Tool Configuration
    }

    try {
        properties([
            parameters([
                string(defaultValue: '', description: 'Enter your BrowserStack Username', name: 'BROWSERSTACK_USERNAME', trim: true),
                string(defaultValue: '', description: 'Enter your BrowserStack Access Key', name: 'BROWSERSTACK_ACCESS_KEY', trim: true),
                choice(
                    choices: [
                        'bstack-single',
                        'bstack-parallel',
                        'bstack-parallel-browsers',
                        'bstack-local',
                        'bstack-local-parallel',
                        'bstack-local-parallel-browsers'
                    ],
                    description: 'Select the test you would like to run',
                    name: 'TEST_TYPE'
                )
            ])
        ])

        stage('Pull code from Github') {
            dir('test') {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/SidGit10/E2E-web-platform.git'
            }
        }

        stage('Run Test') {
            withCredentials([string(credentialsId: '41bfad33-e60a-41c8-b217-17811248a578', variable: 'BROWSERSTACK_ACCESS_KEY')]) {
                def user = "${params.BROWSERSTACK_USERNAME}"
                if (user.contains('-')) {
                    user = user.substring(0, user.lastIndexOf('-'))
                }

                withEnv(['BROWSERSTACK_USERNAME=' + user, 'BROWSERSTACK_ACCESS_KEY=' + "${BROWSERSTACK_ACCESS_KEY}", "PATH+MAVEN=${MAVEN_HOME}/bin"]) {
                    sh '''
                        cd test
                        ln -sf src/test/resources/conf/capabilities/browserstack.yml
                        mvn clean test -P bstack-parallel-browsers
                    '''
                }
            }
        }

        stage('Generate Report') {
            browserStackReportPublisher 'automate'
        }
    } catch (e) {
        currentBuild.result = 'FAILURE'
        echo e.toString()
        throw e
    } finally {
        // notifySlack(currentBuild.result)
    }
}

// def notifySlack(String buildStatus = 'STARTED') {
//     // Build status of null means success.
//     buildStatus = buildStatus ?: 'SUCCESS'

//     def color

//     if (buildStatus == 'STARTED') {
//         color = '#D4DADF'
//     } else if (buildStatus == 'SUCCESS') {
//         color = '#BDFFC3'
//     } else if (buildStatus == 'UNSTABLE') {
//         color = '#FFFE89'
//     } else {
//         color = '#FF9FA1'
//     }

//     def msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}"
//     if (buildStatus != 'STARTED' && buildStatus != 'SUCCESS') {
//         slackSend(color: color, message: msg)
//     }
// }
