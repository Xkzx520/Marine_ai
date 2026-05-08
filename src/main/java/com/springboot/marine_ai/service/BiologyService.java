package com.springboot.marine_ai.service;

import com.springboot.marine_ai.vo.BiologyVO;
import java.util.List;

public interface BiologyService {
    List<BiologyVO> getBiologyList();
    BiologyVO getBiologyByName(String bioName);
}