package com.springboot.marine_ai.service;

import com.springboot.marine_ai.entity.Biology;
import com.springboot.marine_ai.mapper.BiologyMapper;
import com.springboot.marine_ai.vo.BiologyVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BiologyServiceImpl implements BiologyService {

    private final BiologyMapper biologyMapper;

    public BiologyServiceImpl(BiologyMapper biologyMapper) {
        this.biologyMapper = biologyMapper;
    }

    @Override
    public List<BiologyVO> getBiologyList() {
        List<Biology> list = biologyMapper.selectAll();
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public BiologyVO getBiologyByName(String bioName) {
        if (bioName == null || bioName.trim().isEmpty()) {
            throw new IllegalArgumentException("生物名称不能为空");
        }
        Biology biology = biologyMapper.selectByName(bioName);
        if (biology == null) {
            throw new IllegalArgumentException("未找到该海洋生物");
        }
        return convertToVO(biology);
    }

    private BiologyVO convertToVO(Biology biology) {
        BiologyVO vo = new BiologyVO();
        vo.setId(biology.getId());
        vo.setBioName(biology.getBioName());
        vo.setEnName(biology.getEnName());
        vo.setIntro(biology.getIntro());
        vo.setImgUrl(biology.getImgUrl());
        return vo;
    }
}