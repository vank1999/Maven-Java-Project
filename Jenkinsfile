pipeline {
    agent { label 'slave' }
    tools{
        maven 'maven-3.9'
    }
    stages {
        stage('prepare-workspace') {
            steps {
                git url: 'https://github.com/vank1999/Maven-Java-Project.git', branch: 'master'
            }
        }        
    }
}
