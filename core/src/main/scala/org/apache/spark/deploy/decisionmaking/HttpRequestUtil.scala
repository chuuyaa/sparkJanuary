package org.apache.spark.deploy.decisionmaking

import com.fasterxml.jackson.databind.ObjectMapper
import scalaj.http.{Http, HttpOptions}

class HttpRequestUtil {

  def requestEPNValue(requestData: RequestData): Int = {
    val ow = new ObjectMapper().writer().withDefaultPrettyPrinter()
    val json = ow.writeValueAsString(requestData)
    println("THE JSONNN: "+json)

    val result = Http("http://127.0.0.1:5000/api/link").postData(json)
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString

    val EPN = result.body.toInt

    var epn = 1

    if (EPN == 0) {
      epn = 1
    }
    else if (EPN == 1) {
      epn = 2
    }
    else if (EPN == 2) {
      epn = 3
    }
    else if (EPN == 3) {
      epn = 6
    }
    else if (EPN == 4) {
      epn = 7
    }
    epn
  }

}
