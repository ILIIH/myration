pipeline {
    agent any

    tools {
        jdk 'jdk17'
    }

    environment {
        ANDROID_HOME = "/Users/illiabranchuk/Library/Android/sdk"
        PATH = "$ANDROID_HOME/platform-tools:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ILIIH/myration.git'
            }
        }

        stage('Build Debug APK') {
            steps {
                sh './gradlew clean assembleDebug'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/build/outputs/apk/**/*.apk', fingerprint: true
            }
        }
    }
}
