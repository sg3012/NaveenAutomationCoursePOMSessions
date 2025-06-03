pipeline 
{
    agent any
    
    tools{
        maven 'maven3.9.9'
        }

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 bat 'mvn -s "C:\\Users\\sgupta12\\.jenkins\\tools\\hudson.tasks.Maven_MavenInstallation\\maven3.9.9\\conf\\settings.xml" -Dmaven.test.failure.ignore=true clean package'
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa")
            }
        }
            
        stage('Regression Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/sg3012/NaveenAutomationCoursePOMSessions.git'
                    bat 'mvn -s "C:\\Users\\sgupta12\\.jenkins\\tools\\hudson.tasks.Maven_MavenInstallation\\maven3.9.9\\conf\\settings.xml" clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml'
                    
                }
            }
        }
                
     
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        
        stage('Publish Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'reports', 
                                  reportFiles: 'TestExecutionReport.html', 
                                  reportName: 'HTML Regression Extent Report', 
                                  reportTitles: ''])
            }
        }
        
        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }
        
        stage('Sanity Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/sg3012/NaveenAutomationCoursePOMSessions.git'
                    bat 'mvn -s "C:\\Users\\sgupta12\\.jenkins\\tools\\hudson.tasks.Maven_MavenInstallation\\maven3.9.9\\conf\\settings.xml" clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml'
                }
            }
        }
        
        stage('Publish sanity Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'reports', 
                                  reportFiles: 'TestExecutionReport.html', 
                                  reportName: 'HTML Sanity Extent Report', 
                                  reportTitles: ''])
            }
        }
    }
}