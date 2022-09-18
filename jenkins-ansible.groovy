pipeline {
    agent any
    stages {
        stage('Git Clone') {
            steps {
                git credentialsId: 'git-cred', url: 'https://github.com/bayukrsn/ansibleOK.git', branch: 'main'
            }
        }
        stage('Copy File') {
            steps {
                sh """
                cp -f $WORKSPACE/hosts /etc/ansible/hosts
                cp -f $WORKSPACE/playbook-check-ansible.yml /etc/ansible/playbook-check-ansible.yml
                """
            }
        }
        stage("Invoke Playbook"){
            steps{
                ansiblePlaybook colorized: true, inventory: '/etc/ansible/hosts', playbook: '/etc/ansible/playbook-check-ansible.yml'
            }
        }
    }
}
