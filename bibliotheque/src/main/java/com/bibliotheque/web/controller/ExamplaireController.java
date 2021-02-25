package com.bibliotheque.web.controller;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.service.ExamplaireService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examplaire")
public class ExamplaireController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExamplaireService examplaireService;

    @GetMapping
    public List<ExamplaireDTO> getAllExamplaire()
    {
        return examplaireService.getAllExamplaire().stream().map(
                examplaire -> modelMapper.map(examplaire, ExamplaireDTO.class))
                .collect(Collectors.toList());
    }


    @PostMapping
    public ResponseEntity<ExamplaireDTO> createExamplaire(@RequestBody ExamplaireDTO examplaireDTO)
    {
        Examplaire examplaireRequest = modelMapper.map(examplaireDTO,Examplaire.class);

        Examplaire examplaire = examplaireService.createExamplaire(examplaireRequest);

        ExamplaireDTO examplaireResponse = modelMapper.map(examplaire, ExamplaireDTO.class);

        return new ResponseEntity<ExamplaireDTO>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamplaireDTO> getExamplaireById(@PathVariable(name = "id")Long id)
    {
        Examplaire  examplaire = examplaireService.getExamplaireById(id);

        ExamplaireDTO examplaireDTO = modelMapper.map(examplaire,ExamplaireDTO.class);

        return new ResponseEntity<ExamplaireDTO>(examplaireDTO,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamplaireDTO> updateExamplaire(@PathVariable(name = "id") Long id,
                                                       @RequestBody ExamplaireDTO examplaireDTO)
    {
        Examplaire examplaireRequest = modelMapper.map(examplaireDTO,Examplaire.class);

        Examplaire examplaire = examplaireService.updateExamplaire(id, examplaireRequest);

        ExamplaireDTO examplaireResponse = modelMapper.map(examplaire,ExamplaireDTO.class);

        return new ResponseEntity<ExamplaireDTO>(examplaireResponse,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteExamplaire(@PathVariable(name = "id")Long id)
    {
        examplaireService.deleteExamplaire(id);
        return HttpStatus.ACCEPTED;
    }
}
