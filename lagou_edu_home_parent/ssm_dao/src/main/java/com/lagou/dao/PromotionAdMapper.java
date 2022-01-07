package com.lagou.dao;

import com.lagou.domain.PromotionAd;

import java.util.List;

public interface PromotionAdMapper {

    /*
    分页获取所有的广告列表
     */
    public List<PromotionAd> findAllAdByPage();

    /*
    新增广告信息
     */
    public void savePromotionAd(PromotionAd promotionAd);

    /*
    修改广告信息
     */
    public void updatePromotionAd(PromotionAd promotionAd);

    /*
    根据id查询广告信息
     */
    public PromotionAd findPromotionAdById(int id);

    /*
   广告动态上下线
    */
    public void updatePromotionAdStatus(PromotionAd promotionAd);
}
