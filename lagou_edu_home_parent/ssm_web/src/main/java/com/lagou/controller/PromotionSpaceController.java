package com.lagou.controller;

import com.lagou.domain.PromotionSpace;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {
    @Autowired
    private PromotionSpaceService spaceService;

    /*
   获取所有广告位
    */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace(){
        List<PromotionSpace> spaceList = spaceService.findAllPromotionSpace();
        ResponseResult responseResult=new ResponseResult(true,200,"查询广告位成功",spaceList);
        return responseResult;
    }
    /*
        添加广告位
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace){
        if(promotionSpace.getId()==null){
            spaceService.savePromotionSpace(promotionSpace);
            ResponseResult result=new ResponseResult(true,200,"查询广告位成功",null);
            return result;
        }else{
            spaceService.updatePromotionSpace(promotionSpace);
            ResponseResult result=new ResponseResult(true,200,"查询广告位成功",null);
            return result;
        }
    }

    /*
    根据id 查询广告位信息
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(@RequestParam int id){
        PromotionSpace promotionSpace = spaceService.findPromotionSpaceById(id);
        ResponseResult result = new ResponseResult(true,200,"响应成 功",promotionSpace);
        return result;
    }
}
