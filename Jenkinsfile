// vim: set filetype=groovy:
library changelog: false, identifier: 'github.com/ftsell/jenkins-pipeline-library@main', retriever: modernSCM([$class: 'GitSCMSource', credentialsId: '', remote: 'https://github.com/ftsell/jenkins-pipeline-library.git', traits: [gitBranchDiscovery()]])

def imageName = "ghcr.io/ftsell/borrowlist3000"
def imageDigest

pipeline {
    agent {
        kubernetes {
          yaml genPodYaml(true, true)
        }
    }
    options {
        skipDefaultCheckout(true)
    }
    triggers {
      pollSCM 'H * * * *'
    }
    stages {
        stage("Checkout SCM") {
            steps {
                checkout scm
            }
        }
        stage("Validate K8S") {
          steps {
            container("kustomize") {
              checkKustomize()
            }
          }
        }
        stage("Create Container Image") {
            steps {
                container("podman") {
                    script {
                        if (env.TAG_NAME == null && env.BRANCH_IS_PRIMARY == "true") {
                            imageName = "${imageName}:dev-latest"
                        } else if (env.TAG_NAME != null) {
                            imageName = "${imageName}:${env.TAG_NAME}"
                        } else {
                            imageName = "${imageName}:tmp"
                        }
                    }

                    buildContainer(imageName)

                    script {
                      if (env.TAG_NAME != null || env.BRANCH_IS_PRIMARY == "true") {
                        uploadContainer(imageName, "ghcr.io", "github-access")
                      }
                    }
                }
            }
        }
        stage("Deploy") {
          steps {
            container("podman") {
              script {
                imageDigest = fetchImageDigest(imageName, "ghcr.io", "github-access")
              }
            }
            container("kustomize") {
              script {
                if (env.TAG_NAM != null || env.BRANCH_IS_PRIMARY == "true") {
                  deployContainer("borrowlist3000", imageName, imageDigest)
                }
              }
            }
          }
        }
    }
}


