#!groovy

def call(String jobName, String jobNumber, String stageName, String status, String log, String url) {

    def msg = log.bytes.encodeBase64().toString()
    def reqBody = '{"jobName":"' + "${jobName}" + '","jobNumber":"' + "${jobNumber}" + '","stageName":"' + "${stageName}" + '","status":"' + "${status}" + '","log":"' + "${msg}" + '"}'
    def response = httpRequest consoleLogResponseBody: true, contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: "${reqBody}", url: "${url}", validResponseCodes: '200'
    def startRes = readJSON text: "${response.content}"
    if (!"${startRes['state']}" == "200") {
        echo "后台状态回写失败，停止执行..."
        sh("exit 1")
    }
}
