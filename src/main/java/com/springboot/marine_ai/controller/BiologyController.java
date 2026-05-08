package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.service.BiologyService;
import com.springboot.marine_ai.vo.BiologyVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biology")
public class BiologyController {

    private final BiologyService biologyService;

    public BiologyController(BiologyService biologyService) {
        this.biologyService = biologyService;
    }

    @GetMapping("/list")
    public Result<List<BiologyVO>> getBiologyList() {
        try {
            List<BiologyVO> list = biologyService.getBiologyList();
            return Result.success(list);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @GetMapping("/name/{bioName}")
    public Result<BiologyVO> getBiologyByName(@PathVariable String bioName) {
        try {
            BiologyVO biology = biologyService.getBiologyByName(bioName);
            return Result.success(biology);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}