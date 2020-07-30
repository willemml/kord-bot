pipeline {
  agent {
    docker 'gradle:latest'
  }
  stages {
    stage('Gradle Build') {
      steps {
        withGradle {
            sh './gradlew build'
        }
      }
    }
    stage('Rename Jar') {
      steps {
        sh "mv build/libs/nuke-bot-pipeline-all.jar build/libs/nuke-bot-${env.BUILD_NUMBER}.jar"
      }
    }
    stage('Discord') {
      steps {
        withCredentials([string(credentialsId: 'nuke-git-webhook', variable: 'WEBHOOK')]) {
            discordSend description: "Jenkins Pipeline Build", link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: WEBHOOK
        }
      }
    }
  }
  post {
    always {
      archiveArtifacts artifacts: "build/libs/nuke-bot-${env.BUILD_NUMBER}.jar", fingerprint: true, followSymlinks: false, onlyIfSuccessful: true
    }
  }
}
