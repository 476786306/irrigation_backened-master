package com.libv.upload;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@Slf4j
public class UploadFilesUtil {

    public String uploadForImage(MultipartFile imageFile, String imageRootPath) {
        String originalFilename = imageFile.getOriginalFilename();
        String suffix;
        if (StrUtil.isNotEmpty(originalFilename)) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            return null;
        }
        String randomFilename = RandomUtil.randomString("ABCDEFGHJKLMNOPQRSTUVWXYZ0123456789", 6);
        String finalFilename = randomFilename + suffix;
        String targetUrl = imageRootPath + finalFilename;
        File file = new File(targetUrl);
        try {
            imageFile.transferTo(file);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        log.info("上传文件成功--->地址：" + targetUrl);
        return targetUrl;
    }

}
