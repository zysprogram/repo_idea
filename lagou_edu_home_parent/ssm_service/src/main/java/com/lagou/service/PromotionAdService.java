package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;

public interface PromotionAdService {
    /*
    分页获取所有的广告列表
     */
    public PageInfo findAllAdByPage(PromotionAdVO adVo);

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
    public void updatePromotionAdStatus(int id, int status);
}
