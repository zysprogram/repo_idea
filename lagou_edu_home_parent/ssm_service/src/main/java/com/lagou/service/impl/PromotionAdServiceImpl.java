package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.PromotionAdMapper;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PromotionAdServiceImpl implements PromotionAdService {
    @Autowired
    private PromotionAdMapper adMapper;

    /*
    分页获取所有的广告列表
     */
    @Override
    public PageInfo findAllAdByPage(PromotionAdVO adVo) {
        PageHelper.startPage(adVo.getCurrentPage(),adVo.getPageSize());
        //allAdByPage就是已经经过分页查询后的数据封装
        List<PromotionAd> allAdByPage = adMapper.findAllAdByPage();
        //不只需要分页数据，还需要总条数，每页多少条等信息，需要PageInfo对象
        PageInfo<PromotionAd> adPageInfo = new PageInfo<>(allAdByPage);
        return adPageInfo;
    }

    /*
    新增广告信息
     */
    @Override
    public void savePromotionAd(PromotionAd promotionAd) {
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);
        adMapper.savePromotionAd(promotionAd);
    }

    /*
   修改广告信息
    */
    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {
        Date date = new Date();
        promotionAd.setUpdateTime(date);
        adMapper.updatePromotionAd(promotionAd);
    }

    /*
   根据id查询广告信息
    */
    @Override
    public PromotionAd findPromotionAdById(int id) {
        PromotionAd promotionAd = adMapper.findPromotionAdById(id);
        return promotionAd;
    }

    /*
   广告动态上下线
    */
    @Override
    public void updatePromotionAdStatus(int id, int status) {
        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setId(id);
        promotionAd.setStatus(status);
        promotionAd.setUpdateTime(new Date());
        adMapper.updatePromotionAdStatus(promotionAd);
    }
}
