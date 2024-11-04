package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.Message;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import routes.Routes;
import security.controllers.AccessController;
import security.controllers.SecurityController;
import security.enums.Role;
import exceptions.ApiException;
import security.routes.SecurityRoutes;
import utils.Utils;

import java.time.LocalDateTime;

public class ApplicationConfig {

    private static Routes routes = new Routes();
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static SecurityController securityController = SecurityController.getInstance();
    private static AccessController accessController = new AccessController();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    public static void configuration(JavalinConfig config) {
        config.showJavalinBanner = false;
        config.bundledPlugins.enableRouteOverview("/routes", Role.ANYONE);

        //TODO: IMPORTANT: ændring tik hvad min endpoint route skal hedder
        config.router.contextPath = "/api"; // base path for all endpoints

        config.router.apiBuilder(routes.getRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
    }

    public static Javalin startServer(int port) {
        Javalin app = Javalin.create(ApplicationConfig::configuration);

        app.beforeMatched(accessController::accessHandler);

        app.beforeMatched(ctx -> accessController.accessHandler(ctx));

        //Vores exception bliver fanget her
        app.exception(Exception.class, ApplicationConfig::generalExceptionHandler);
        app.exception(ApiException.class, ApplicationConfig::apiExceptionHandler);
        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }


    //TODO: IMPORTANT: ændre denne for apiException (måde den skal printe ud på)
    public static void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getStatusCode());
        logger.warn("An API exception occurred: Code: {}, Message: {}, timestamp: {}", e.getStatusCode(), e.getMessage(), e.getTimestamp());

        // Return a structured error response
        ctx.json(new Message(e.getStatusCode(), e.getMessage(), e.getTimestamp()));
    }

    private static void generalExceptionHandler(Exception e, Context ctx) {
        logger.error("An unhandled exception occurred", e); // Log entire exception for better debugging
        ctx.status(500); // Set to Internal Server Error status
        ctx.json(new Message(500, "An internal server error occurred", LocalDateTime.now()));
    }
}
