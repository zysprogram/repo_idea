package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {
    @Autowired
    private PromotionAdService adService;
    /*
    分页获取所有的广告列表
     */
    @RequestMapping("/findAllPromotionAdByPage")
    //没有请求体不需要@RequestBody这个注解
    public ResponseResult findAllAdByPage(PromotionAdVO adVo) {
        PageInfo allAdByPage = adService.findAllAdByPage(adVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", allAdByPage);
        return responseResult;
    }

    /**
     * 广告图片上传
     */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.判断接收到的文件是否为空
        if(file.isEmpty()){
            throw new RuntimeException();
        }

        //2.获取项目部署路径
        // D:\apache-tomcat-8.5.56\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");

        //3.截取路径获取到webapps目录
        String subString = realPath.substring(0, realPath.indexOf("ssm_web"));

        //4.获取源文件名
        String originalFilename = file.getOriginalFilename();

        //5.生成新文件名
        String newFileName=System.currentTimeMillis()+originalFilename.substring(originalFilename.lastIndexOf("."));

        //6.上传文件到服务器
        String uploadPath=subString+"upload\\";
        File filePath = new File(uploadPath, newFileName);
        //如果目录不存在就创建目录
        if(!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录："+filePath);
        }
        //图片上传
        file.transferTo(filePath);

        //7.将文件名和文件路径返回进行响应
        Map<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","https://localost:8080/upload/"+newFileName);
        ResponseResult responseResult = new ResponseResult(true, 200, "图片上传成功", map);
        return responseResult;
    }

    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {
        if (promotionAd.getId() == null) {
            adService.savePromotionAd(promotionAd);
            ResponseResult result = new ResponseResult(true, 200, "新增响应成功", null);
            return result;
        }else{
            adService.updatePromotionAd(promotionAd);
            ResponseResult result = new ResponseResult(true, 200, "修改响应成功", null);
            return result;
        }
    }

    /*
  根据id查询广告信息
   */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(@RequestParam int id){
        PromotionAd promotionAd = adService.findPromotionAdById(id);
        ResponseResult result = new ResponseResult(true,200,"响应成 功",promotionAd);
        return result;
    }

    /*
   广告动态上下线
    */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updateCourseStatus(@RequestParam int id, @RequestParam int status) {
        adService.updatePromotionAdStatus(id,status);
        Map<String, Integer> map = new HashMap<>();
        map.put("status", status);
        ResponseResult result = new ResponseResult(true, 200, "响应成功", map);
        return result;
    }
}
