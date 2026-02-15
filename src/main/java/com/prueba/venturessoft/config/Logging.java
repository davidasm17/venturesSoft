package com.prueba.venturessoft.config;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class Logging {
	private final ObjectMapper objectMapper;

    public static final String MENSAJE_REQUEST = "Request: Clase: {} -> Metodo: {}";
    public static final String LOG_PARAMETROS = "Parámetros: {}"; 
    public static final String MENSAJE_RESPONSE = "Response [{}]: Clase: {} -> Metodo: {} (Tiempo: {}ms)";
    public static final String LOG_BODY = "Body: {}";
    public static final String LOG_ERROR = "Error en ejecución: {}";

    @Pointcut("bean(*Controller)")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info(MENSAJE_REQUEST, className, methodName);

        if (args.length > 0) {
            try {
                String argsJson = objectMapper.writeValueAsString(args);
                log.info(LOG_PARAMETROS, argsJson);
            } catch (Exception e) {
                log.info(LOG_PARAMETROS, Arrays.toString(args));
            }
        }

        long start = System.currentTimeMillis();
        Object result = null;

        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - start;
            
            String status = "OK";
            if (result instanceof ResponseEntity<?>) {
                status = ((ResponseEntity<?>) result).getStatusCode().toString();
            }

            log.info(MENSAJE_RESPONSE,status, className, methodName, executionTime);

            if (result != null) {
                if ("login".equals(methodName)) {
                    log.info(LOG_BODY, "[PROTEGIDO - TOKEN OCULTO]");
                } else {
                    procesarBody(result);
                }
            }

            return result;

        } catch (Throwable e) {
            long executionTime = System.currentTimeMillis() - start;
            log.error(MENSAJE_RESPONSE, className, methodName, executionTime);
            log.error(LOG_ERROR, e.getMessage());
            
            throw e; 
        }
    }

    private void procesarBody(Object result) {
        try {
            Object bodyToLog = result;
            
            if (result instanceof ResponseEntity<?>) {
            	ResponseEntity<?> response = (ResponseEntity<?>) result;
            	if (response.getStatusCode().is2xxSuccessful() && response.getBody() == null) {
            		 return;
            	}
                bodyToLog = response.getBody();
            }

            if (bodyToLog == null) {
                log.warn(LOG_BODY, "[NULL] No se encontraron datos para estos parámetros.");
                return;
            }
            if (bodyToLog instanceof List && ((List<?>) bodyToLog).isEmpty()) {
                log.warn(LOG_BODY, "[LISTA VACÍA] La búsqueda no arrojó resultados.");
                return;
            }

            String bodyJson = objectMapper.writeValueAsString(bodyToLog);
            if (bodyJson.length() > 1000) {
                bodyJson = bodyJson.substring(0, 1000) + "... [TRUNCADO]";
            }
            log.info(LOG_BODY, bodyJson);

        } catch (Exception e) {
            log.info(LOG_BODY, result);
        }
    }
}
