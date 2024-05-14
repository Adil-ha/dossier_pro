package com.adil.blog.controller.advice;

import com.adil.blog.exception.DuplicateEmailException;
import com.adil.blog.exception.InvalidEmailException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.security.SignatureException;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public @ResponseBody
    ProblemDetail BadCredentialsException(final BadCredentialsException exception){
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                UNAUTHORIZED,
                "identifiants invalides"
        );
        problemDetail.setProperty("erreur", "Nous n'avons pas pu vous identifier !");
        return problemDetail;
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = {SignatureException.class, MalformedJwtException.class})
    public @ResponseBody
    ProblemDetail BadCredentialsException(final Exception exception){
        return ProblemDetail.forStatusAndDetail(
                UNAUTHORIZED,
                "Token invalide !"
        );
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public @ResponseBody
    ProblemDetail BadCredentialsException(final AccessDeniedException exception){
       return ProblemDetail.forStatusAndDetail(
                FORBIDDEN,
                "Vos droits ne vous permettent pas d'effectuer cette action !"
        );

    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = DuplicateEmailException.class)
    public @ResponseBody
    ProblemDetail BadCredentialsException(final DuplicateEmailException exception){
       return ProblemDetail.forStatusAndDetail(
                FORBIDDEN,
                "Votre email existe déjà !"
        );

    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = InvalidEmailException.class)
    public @ResponseBody
    ProblemDetail BadCredentialsException(final InvalidEmailException exception){
        return ProblemDetail.forStatusAndDetail(
                FORBIDDEN,
                "Le format de votre email est invalide !"
        );

    }



    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = Exception.class)
    public Map<String, String> exceptionsHandler(){
        return Map.of("Erreur", "Description");
    }

}
