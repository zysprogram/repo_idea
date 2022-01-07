package com.lagou.service.impl;

import com.lagou.dao.PromotionSpaceMapper;
import com.lagou.domain.PromotionSpace;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PromotionSpaceServiceImpl implements PromotionSpaceService {
    @Autowired
    private PromotionSpaceMapper spaceMapper;
    /*
    获取所有广告位
     */
    @Override
    public List<PromotionSpace> findAllPromotionSpace() {
        List<PromotionSpace> list = spaceMapper.findAllPromotionSpace();
        return list;
    }

    /*
    添加广告位
     */
    @Override
    public void savePromotionSpace(PromotionSpace promotionSpace) {
        //封装数据
        Date date =new Date();
        promotionSpace.setCreateTime(date);
        promotionSpace.setUpdateTime(date);
        promotionSpace.setIsDel(0);
        promotionSpace.setSpaceKey(UUID.randomUUID().toString());
        //调用方法
        spaceMapper.savePromotionSpace(promotionSpace);
    }

    /*
    修改广告位
     */
    @Override
    public void updatePromotionSpace(PromotionSpace promotionSpace) {
        //封装数据
        Date date =new Date();
        promotionSpace.setUpdateTime(date);
        //调用方法
        spaceMapper.updatePromotionSpace(promotionSpace);
    }

    /*
    根据id 查询广告位信息
     */
    @Override
    public PromotionSpace findPromotionSpaceById(int id) {
        PromotionSpace promotionSpace = spaceMapper.findPromotionSpaceById(id);
        return promotionSpace;
    }
}
