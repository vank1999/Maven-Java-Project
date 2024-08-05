def ansible = [:]
         ansible.name = 'ansible'
         ansible.host = '172.31.19.53'
         ansible.user = 'ubuntu'
         ansible.allowAnyHosts = true
pipeline {
    agent { label 'slave' }
    tools{
        maven 'maven-3.9'
    }
    stages {
        stage('prepare-workspace') {
            steps {
                git credentialsId: 'git', url: 'https://github.com/vank1999/Maven-Java-Project.git'
            }
        }        
    }
}
