def ansible = [:]
         ansible.name = 'ansible'
         ansible.host = '172.31.19.53'
         ansible.user = 'ubuntu'
         //ansible.password = 'admin123'
         ansible.allowAnyHosts = true

def kops = [:]
         kops.name = 'kops'
         kops.host = '172.31.42.19'
         kops.user = 'ubuntu'
         //kops.password = 'admin123'
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
            }
        }

        stage('tools-setup') {
            steps {
            echo "Tools Setup"
            sshCommand remote: ansible, command: 'cd Maven-Java-Project; git pull'
            sshCommand remote: ansible, command: 'cd Maven-Java-Project; ansible-playbook -i hosts tools/sonarqube/sonar-install.yaml'
            sshCommand remote: ansible, command: 'cd Maven-Java-Project; ansible-playbook -i hosts tools/docker/docker-install.yml'  
            
            //K8s Setup
           sshCommand remote: kops, command: "cd Maven-Java-Project; git pull"
	       sshCommand remote: kops, command: "kubectl apply -f Maven-Java-Project/k8s-code/staging/namespace/staging-ns.yml"
	       sshCommand remote: kops, command: "kubectl apply -f Maven-Java-Project/k8s-code/prod/namespace/prod-ns.yml"
            }
        }        
    }
}
