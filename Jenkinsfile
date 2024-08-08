def ansible = [:]
         ansible.name = 'ansible'
         ansible.host = '172.31.19.53'
         ansible.user = 'ubuntu'
         ansible.identityFile = '~/.ssh/id_rsa' // Path to the private key
         ansible.allowAnyHosts = true

def kops = [:]
         kops.name = 'kops'
         kops.host = '172.31.42.19'
         kops.user = 'ubuntu'
         kops.identityFile = '~/.ssh/id_rsa'
         kops.allowAnyHosts = true

pipeline {
    agent { label 'slave' }
    tools{
        maven 'maven-3.9'
    }
    stages {
        stage('prepare-workspace') {
            steps {
                git credentialsId: 'git', url: 'https://github.com/vank1999/Maven-Java-Project.git'
                stash 'Source'
            }
        }

        stage('tools-setup') {
            steps {
            echo "Tools Setup"
            //sshCommand remote: ansible, command: 'cd Maven-Java-Project; git pull'
            //sshCommand remote: ansible, command: 'cd Maven-Java-Project; ansible-playbook -i hosts tools/sonarqube/sonar-install.yaml'
            //sshCommand remote: ansible, command: 'cd Maven-Java-Project; ansible-playbook -i hosts tools/docker/docker-install.yml'  
            
            //K8s Setup
           //sshCommand remote: kops, command: "cd Maven-Java-Project; git pull"
	       //sshCommand remote: kops, command: "kubectl apply -f Maven-Java-Project/k8s-code/staging/namespace/staging-ns.yml"
	       //sshCommand remote: kops, command: "kubectl apply -f Maven-Java-Project/k8s-code/prod/namespace/prod-ns.yml"
            }
        }

        stage('sonarqube analysis'){
            steps{
                 echo "Sonar Scanner"
                 sh "mvn clean compile"
                 withSonarQubeEnv('sonarqube') { 
                 sh "mvn sonar:sonar "
                }
            }
        }

        stage('Unit Test Cases') {
          steps{
	       echo "Clean and Test"
              sh "mvn clean install"  
          }
          post{
              success{
		      echo "Clean and Test"
                  junit 'target/surefire-reports/*.xml'
              }
          }
      }

      stage('Build Code') {
        
          steps{
	      unstash 'Source'
              sh "mvn clean package"  
          }
          post{
              success{
                  archiveArtifacts '**/*.war'
              }
          }
      }

    }
}
