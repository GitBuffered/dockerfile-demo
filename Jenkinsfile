pipeline {
    agent any

    environment {
        GIT_URL = 'https://github.com/GitBuffered/dockerfile-demo.git'
        GIT_BRANCH_SIT = 'develop'
        PROFILE = """${sh(
                           returnStdout: true,
                           script: 'if [[ ${GIT_BRANCH}  =~ ^origin/release/.*  ]]; then echo "uat" & elif [[ ${GIT_BRANCH}  == origin/develop ]]; then echo "test" & else echo "dev" & fi'
                     ).trim()}"""

        VERSION_NO = """${sh(
                           returnStdout: true,
                           script: 'if [[ ${GIT_BRANCH}  =~ ^origin/release/.*  ]]; then md=`date +%m%d`; echo "1.4.${md}" & elif [[ ${GIT_BRANCH}  == origin/develop ]]; then echo "1.4.4" & else echo "1.4.3" & fi'
                     ).trim()}"""
        BRANCH_NAME = """${sh(
                           returnStdout: true,
                           script: 'echo "${GIT_BRANCH}"|cut -d / -f 2 '
                       ).trim()}"""
    }

    stages {
            stage('Checkout from scm') {
                failFast true
                parallel {
                    stage('checkout for uat') {
                        when {
                            expression {
                                "${PROFILE}" == 'uat'
                            }
                        }
                        steps {
                            deleteDir()
                            checkout(
                                [$class: 'GitSCM', branches: [[name: "release/${VERSION_NO}"]],
                                doGenerateSubmoduleConfigurations: false,
                                extensions: [],
                                submoduleCfg: [],
                                userRemoteConfigs: [[credentialsId: 'gitlabUP', url: "${GIT_URL}"]]]
                                )
                        }
                    }
                    stage('checkout for sit') {
                        when {
                            expression {
                                "${PROFILE}" == 'test'
                            }
                        }
                        steps {
                            deleteDir()
                            checkout(
                                [$class: 'GitSCM', branches: [[name: "${GIT_BRANCH_SIT}"]],
                                doGenerateSubmoduleConfigurations: false,
                                extensions: [],
                                submoduleCfg: [],
                                userRemoteConfigs: [[credentialsId: 'gitlabUP', url: "${GIT_URL}"]]]
                                )
                        }
                    }
                }
            }
            stage('Build') {
                steps {
                    //mvn package
                    sh "mvn -U clean package -D maven.test.skip -P ${PROFILE} -s /opt/software/maven/maven/conf/settings.xml"
                    echo 'Build Success!'
                }
            }

            stage('Test') {
                steps {
                    echo 'Testing TODO'
                }
            }
        }
}