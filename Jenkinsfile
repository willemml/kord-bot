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
  }
  post {
    always {
      archiveArtifacts artifacts: "build/libs/nuke-bot-${env.BUILD_NUMBER}.jar", fingerprint: true, followSymlinks: false, onlyIfSuccessful: true
    }
    stage('Discord notify'){
        def artifactUrl = env.BUILD_URL + "artifact/"
        def msg = "**Status:** " + currentBuild.currentResult.toLowerCase() + "\n"
        msg += "**Branch:** ${branch}\n"
        msg += "**Changes:** \n"
        if (!currentBuild.changeSets.isEmpty()) {
            currentBuild.changeSets.first().getLogs().each {
                msg += "- `" + it.getCommitId().substring(0, 8) + "` *" + it.getComment().substring(0, it.getComment().length()-1) + "*\n"
            }
        } else {
            msg += "no changes for this run\n"
        }

        if (msg.length() > 1024) msg.take(msg.length() - 1024)

        def filename
        msg += "\n **Artifacts:**\n"
        currentBuild.rawBuild.getArtifacts().each {
            filename = it.getFileName()
            msg += "- [${filename}](${artifactUrl}${it.getFileName()})\n"
        }

        withCredentials([string(credentialsId: 'discord-webhook', variable: 'discordWebhook')]) {
            discordSend thumbnail: "http://wnuke.dev/radiation-symbol.png", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), description: "${msg}", link: env.BUILD_URL, title: "nuke-bot:${branch} #${BUILD_NUMBER}", webhookURL: "${discordWebhook}"
        }
  }
}

