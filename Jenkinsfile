node('test'){
    parameters {
        string(name: 'USERNAME', defaultValue: 'test', description: 'Basic Auth Username')
        password(name: 'PASSWORD', defaultValue: 'test', description: 'Basic Auth Password')
        string(name: 'BASE_URL', defaultValue: 'http://localhost:3001', description: 'Base Url')
        string(name: 'TEST_CONFIG', defaultValue: 'configs/load', description: 'Choose right configuration project dir: src/gatling/resources/configs/<config_name>.conf')
        string(name: 'EXIT_ON_FAILURE', defaultValue: 'true', description: 'Stop Experiment if more than 1 request failed.')
        string(name: 'GRAPHITE_HOST', defaultValue: 'localhost', description: 'Metrics host')
        string(name: 'GRAPHITE_PORT', defaultValue: '2004', description: 'influx db port')
        string(name: 'INFLUX_PREFIX', defaultValue: 'v2.gatling.load.localhost', description: 'Prefix for Gatling Test for monitoring. Format:  v2.gatling.<experiment_name>.<env_name>')
        string(name: 'RESULTS_DIR', defaultValue: 'results', description: 'Results Dir')
        string(name: 'RESOURCES_DIR', defaultValue: 'src/gatling/resources', description: 'Path to Project resources dir')
    }
    environment {
        USERNAME = "${params.USERNAME}"
        PASSWORD = "${params.PASSWORD}"
        BASE_URL = "${params.BASE_URL}"
        RESULTS_DIR = "${params.RESULTS_DIR}"
    }

    stage("checkout git"){
        // git branch: 'master', credentialsId: 'github_token', url: 'https://github.com/savvagen/gatling-gradle-demo.git'
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', noTags: true, reference: '', shallow: true]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github_token', name: "", url: 'https://github.com/savvagen/gatling-gradle-demo.git']]])
    }

    /*stage('setup data'){
        catchError {
            sh "./gradlew clean gatlingRun-org.example.simulations.SetUpSimulation"
        }
    }*/

    stage('load_test'){
        environment {
            TEST_CONFIG = "${params.TEST_CONFIG}"
            GRAPHITE_HOST = "${params.GRAPHITE_HOST}"
            GRAPHITE_PORT = "${params.GRAPHITE_PORT}"
            INFLUX_PREFIX = "${params.INFLUX_PREFIX}"
        }
        catchError {
            sh "./gradlew clean gatlingRun"
            // Run Specific Simulation
            // sh 'gatlingRun-org.example.simulations.LoadSimulation'
        }
        catchError {
            sh './gradlew downloadGatling gatlingReport'
        }
    }

    /*stage('cleanup data'){
        catchError {
            sh "./gradlew gatlingCleanup"
        }
    }*/

    stage('gatling report'){
        gatlingArchive()
    }

    stage('publish report'){
        String resultsDir = "${params.RESULTS_DIR}"
        String resourcesDir = "${params.RESOURCES_DIR}"
        step([$class: 'ArtifactArchiver', artifacts: "${resultsDir}/**/*" ])
        step([$class: 'ArtifactArchiver', artifacts: "${resourcesDir}/*.xml, ${resourcesDir}/*.conf, ${resourcesDir}/*.log"])
        sh "cp -avr ${resultsDir}/*/ ./${resultsDir}/report"
        publishHTML([
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: "${resultsDir}/report",
            reportFiles: 'index.html, req_*.html',
            reportName: 'Gatling HTML Report',
            reportTitles: 'PerformanceReport'
        ])
    }

    stage('junit report'){
        String resultsDir = "${params.RESULTS_DIR}"
        junit "${resultsDir}/report/js/assertions.xml"
    }

    stage('cleanup workspace dir'){
        echo "${WORKSPACE}"
        echo env.WORKSPACE
        // Important for generating Gatling reports!
        dir('/home/jenkins/workspace/test') {
            deleteDir()
        }
    }

}