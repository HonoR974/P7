package com.bibliotheque.web.controller;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @GetMapping("/retards")
    public ResponseEntity<List<PretBatchDTO>> getPretRetards()
    {

        List<PretBatchDTO> list = batchService.getPretRetard();

        System.out.println( "\n liste dans le controller  " +  list.toString() );

        return new ResponseEntity<List<PretBatchDTO>>(list, HttpStatus.ACCEPTED);
    }
}
