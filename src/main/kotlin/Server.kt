import io.vertx.core.*
import io.vertx.core.http.*
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import java.util.concurrent.Executors


class Server: AbstractVerticle() {


    override  fun start() {
        var executor = Executors.newFixedThreadPool(8);

        var httpServer = vertx.createHttpServer()
        val httpRouter = Router.router(vertx)
        httpRouter.route().handler(BodyHandler.create())
        httpRouter.post("/send")
            .handler { req ->
                println(req.toString())
                val json = req.bodyAsJson
                println(json)
                req.response().end("okk")
            }
        httpRouter.post("/Authorization")
            .handler { req ->

                val json = req.bodyAsJson
                if (json != null){
                    var put = req.normalisedPath().substring(1, req.normalisedPath().length)
                     var po =   authorization(json, put)
                    req.response().end(po)
                    println("job.toString()")
                }else{
                    req.response().end("Нет json")
                }
            }

        httpRouter.post("/RaspDepartment")
            .handler { req ->

                val json = req.bodyAsJson
                if (json != null){
                    var put = req.normalisedPath().substring(1, req.normalisedPath().length)
                    var po =   authorization(json, put)
                    req.response().end(po)
                    println("job.toString()")
                }else{
                    req.response().end("Нет json")
                }
            }
        httpServer.requestHandler(Handler<HttpServerRequest> { httpRouter.accept(it) })
        httpServer.listen(6792)

//        super.start()
    }



        fun authorization (json : JsonObject, put : String): String {

            var wwwClient = WebClientMy(put.toString() + "/", json, vertx)
            var answer =   wwwClient.auth()

           return answer
     }



}


    fun main(args: Array<String>){
        val options = VertxOptions()
        options.maxEventLoopExecuteTime = java.lang.Long.MAX_VALUE
        val vertx = Vertx.vertx(options).deployVerticle(Server(), DeploymentOptions().apply {
            isWorker = true
        })

    }

