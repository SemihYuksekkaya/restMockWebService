package com.restmockservice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * All methods:
 * -Accept "get,post,put,delete" httpMethods
 * -return json or xml response from a given file in the "responses" folder,
 * named same as the path with some character exceptions,
 * Encoded as:
 * "/" to "_"
 * "?query" to "{query}"
 */
@RestController
public class serviceImpl {

    @GetMapping("/**")
    public ResponseEntity<String> getService(HttpServletRequest request) {
        return service("get", request);
    }

    @PostMapping("/**")
    public ResponseEntity<String> postService(HttpServletRequest request) {
        return service("post", request);
    }

    @PutMapping("/**")
    public ResponseEntity<String> putService(HttpServletRequest request) {
        return service("put", request);
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> deleteService(HttpServletRequest request) {
        return service("delete", request);
    }

    @PatchMapping("/**")
    public ResponseEntity<String> patchService(HttpServletRequest request) {
        return service("patch", request);
    }

    /**
     *
     * @param crud  Specifies the crud method.
     * @param request HttpServletRequest
     * @return  ResponseEntity from the specified file. Returns NOT_ACCEPTABLE httpStatus when response file is not found.
     */
    protected ResponseEntity<String> service(String crud, HttpServletRequest request) {

        String response;
        String file;
        String queryStr;
        queryStr = request.getQueryString();
        if (queryStr == null) {
            file = request.getRequestURI();
        } else {
            file = (request.getRequestURI() + "{" + queryStr+"}");
        }
        file = file.replaceAll("/", "_")
                .substring(1);

        try {
            response = Files.readString(Paths.get("responses/" + crud + file + ".json"));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);

        } catch (IOException e) {
            try {
                response = Files.readString(Paths.get("responses/" + crud + file + ".xml"));
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(response);

            } catch (IOException ex) {
                response = "File: " + crud + file + " (.json or .xml)" + " is not found.";
                //response String returned as Etag.
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, response)).build();
            }
        }
        catch (Exception exc){
            response = "File: " + crud + file + " (.json or .xml)" + " is not acceptable Exception Message: "+exc.getMessage();
            //response String returned as Etag.
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, response)).build();
        }
    }
}
