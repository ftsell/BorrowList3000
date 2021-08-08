// vim: set filetype=groovy:

pipeline {
    agent {
        kubernetes {
            yaml """
kind: Pod
spec:
  containers:
    - name: kustomize
      image: docker.io/nekottyo/kustomize-kubeval
      tty: true
      command:
      - cat
    - name: podman
      image: quay.io/podman/stable
      tty: true
      securityContext:
        privileged: true
      command:
        - cat
"""
        }
    }
    options {
        skipDefaultCheckout(true)
    }
    stages {
        stage("Checkout SCM") {
            steps {
                checkout scm
            }
        }
        stage("Check Kubernetes Config validity") {
            steps {
                container("kustomize") {
                    gitStatusWrapper(
                        credentialsId: 'github-access',
                        description: 'Validates the generated kubernetes config',
                        failureDescription: 'kubernetes config is not valid',
                        successDescription: 'kubernetes config is valid',
                        gitHubContext: 'check-k8s'
                    ) {
                        sh "kustomize build . > k8s.yml"
                        sh "kubeval k8s.yml --strict"
                    }
                }
            }
        }
        stage("Build Container Image") {
            steps {
                container("podman") {
                    gitStatusWrapper(
                        credentialsId: "github-access",
                        description: "Builds the container image",
                        failureDescription: "Container image failed to build",
                        successDescription: "Container image was successfully built",
                        gitHubContext: "build-container-image"
                    ) {
                        // sometimes node-gyp tries to locate python which is not present in the build container
                        // i don't know why but that's why we retry the build up to 3 times
                        retry(3) {
                            sh "podman build -t borrowlist3000 ."
                        }
                    }
                }
            }
        }
        stage("Upload Container Image") {
            steps {
                container("podman") {
                    gitStatusWrapper(
                        credentialsId: "github-access",
                        description: "Uploads the container image",
                        failureDescription: "Could not upload the container image",
                        successDescription: "Container image was uploaded",
                        gitHubContext: "upload-container-image"
                    ) {
                        milestone(ordinal: 100)

                        // always upload to my own private registry
                        withCredentials([usernamePassword(
                            credentialsId: 'registry-credentials',
                            passwordVariable: 'registry_password',
                            usernameVariable: 'registry_username'
                        )]) {
                            sh "podman login registry.finn-thorben.me -u $registry_username -p $registry_password"
                            sh "podman tag borrowlist3000 registry.finn-thorben.me/borrowlist3000"
                            sh "podman push registry.finn-thorben.me/borrowlist3000"
                        }

                        script {
                            withCredentials([usernamePassword(
                                credentialsId: 'github-access',
                                passwordVariable: 'registry_password',
                                usernameVariable: 'registry_username'
                            )]) {
                                if (env.TAG_NAME == null) {
                                    // commit events get pushed as :dev-latest
                                    sh "podman login ghcr.io -u $registry_username -p $registry_password"
                                    sh "podman tag borrowlist3000 ghcr.io/ftsell/borrowlist3000:dev-latest"
                                    sh "podman push ghcr.io/ftsell/borrowlist3000:dev-latest"

                                } else {
                                    // tag events get pushed as the corresponding tag and :latest
                                    sh "podman login ghcr.io -u $registry_username -p $registry_password"
                                    sh "podman tag borrowlist3000 ghcr.io/ftsell/borrowlist3000:${env.TAG_NAME}"
                                    sh "podman push ghcr.io/ftsell/borrowlist3000:${env.TAG_NAME}"

                                    sh "podman tag borrowlist3000 ghcr.io/ftsell/borrowlist3000:latest"
                                    sh "podman push ghcr.io/ftsell/borrowlist3000:latest"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

