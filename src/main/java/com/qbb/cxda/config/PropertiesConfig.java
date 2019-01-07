package com.qbb.cxda.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class PropertiesConfig {

    //轮转图片的路径前缀
    @Value("${localServer.prixPath}")
    public String prixPath;

    @Value("${web.upload.path}")
    public String uploadNewsPath;


}
