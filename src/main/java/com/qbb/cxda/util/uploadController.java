package com.qbb.cxda.util;

import com.qbb.cxda.base.ResultObject;
import com.qbb.cxda.config.PropertiesConfig;
import com.qbb.cxda.constant.BaseEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
public class uploadController {

    @Autowired
    PropertiesConfig propertiesConfig;

    @PostMapping(value="/uploadEditorPic")
    public ResultObject uploadFile(@RequestParam(value="myFileName")MultipartFile mf) {
        if(mf == null ){
            return new ResultObject(BaseEnums.PARAM_ERROR,"上传的文件有误");
        }
        //获取源文件
        String filename = mf.getOriginalFilename();
        String[] names=filename.split("\\.");//
        String tempNum=(int)(Math.random()*100000)+"";
        Date now = new Date();
        //文件以年/月/文件名形式保存：/2018/12/xxxxx.jpg
        String dir = propertiesConfig.uploadNewsPath + "news/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_YEAR)+"/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_MONTH)+"/";
        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        String uploadFileName =  "employee/" + DateUtil.format(now,DateUtil.Pattern.NONE_YEAR)+"/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_MONTH)+"/"
                +DateUtil.format(now,DateUtil.Pattern.NONE_DATETIME_SSS)+"."+names[names.length-1];
        File targetFile = new File (propertiesConfig.uploadNewsPath,uploadFileName);//目标文件

        //开始从源文件拷贝到目标文件
        //传图片一步到位
        try {
            mf.transferTo(targetFile);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = propertiesConfig.prixPath + uploadFileName;
        return new ResultObject(BaseEnums.SUCCESS,url);//将图片地址返回
    }

}
