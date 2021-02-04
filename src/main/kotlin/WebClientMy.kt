
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.*




class WebClientMy(private var put : String, private var json : JsonObject, private var vertx : Vertx)  {

    private val host = "http://192.168.173.101/StomWork/hs/"





    var client = WebClient.create(vertx)


        fun auth() : String  {
            var ответ = ""




           var счет : Int = 0
           GlobalScope.launch { ответ = zapros1C() }
            do {
                счет++
                Thread.sleep(200)
            }while (ответ.equals("") &&  (счет < 31))
            println(счет)




         return ответ
    }


     suspend fun zapros1C() : String = suspendCoroutine {
        cout ->
         println(put.toString())
            client
                .postAbs(host + put)
                .putHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString("web:123".toByteArray()) )
                .sendJsonObject(json) { rrr ->
                    if(rrr.succeeded()) {

                        var response = rrr.result()

                        var body = response.body()

                        var  ответ = rrr.result().bodyAsString().toString()

                        cout.resume(ответ)
                    }
                    else{
                        cout.resume("------------")
                    }
        }

    }


}

