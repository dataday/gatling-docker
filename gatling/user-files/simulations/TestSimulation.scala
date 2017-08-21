import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class TestSimulation extends Simulation {

  val headers = Map(
    "Authorization" -> """Bearer TOKEN""",
    "X-API-Key" -> """API-KEY""",
    "X-Authentication-Provider" -> """PROVIDER""",
    "Keep-Alive" -> """150"""
  )

  val baseHttpProtocol = http.baseURL("""SCHEMA://HOST:PORT""")
    .headers(headers)
    .userAgentHeader(
      """Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:46.0) Gecko/20100101 Firefox/46.0"""
    )

  val baseRequest = scenario("GET Base URI")
    .exec(http("""Host, Base URI As JSON""")
      .get("""/""")
      .check(status is 200)
      .asJSON
      .check(jsonPath("$.results"))
    )

  setUp(
    baseRequest.inject(
      constantUsersPerSec(1) during(20 seconds)
    ).protocols(baseHttpProtocol)
  )
}
