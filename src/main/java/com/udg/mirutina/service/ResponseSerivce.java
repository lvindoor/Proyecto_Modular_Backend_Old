package com.udg.mirutina.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.udg.mirutina.pojo.Response;

@Service
public class ResponseSerivce {

  public ResponseEntity<Response> errorResponse(String error) {
    List<String> errorsList = new ArrayList<>();
    errorsList.add(error);

    Map<String, Object> errors = new HashMap<>();
    errors.put("errors", errorsList);

    return new ResponseEntity<Response>(
        new Response(false, "error", errors), HttpStatus.BAD_REQUEST);
  }

  public Map<String, Object> errors(String error) {
    List<String> errorsList = new ArrayList<>();
    errorsList.add(error);

    Map<String, Object> errors = new HashMap<>();
    errors.put("errors", errorsList);
    return errors;
  }

  public Map<String, Object> errors(String[] errorArr) {
    List<String> errorsList = new ArrayList<>();

    for (String error : errorArr) {
      errorsList.add(error);
    }

    Map<String, Object> errors = new HashMap<>();
    errors.put("errors", errorsList);
    return errors;
  }

}
