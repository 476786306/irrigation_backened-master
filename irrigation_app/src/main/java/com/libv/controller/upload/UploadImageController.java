package com.libv.controller.upload;

import com.libv.upload.UploadFilesUtil;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadImageController {

    @Value("${image-path}")
    private String imageRootPath;

    @Autowired
    UploadFilesUtil uploadFilesUtil;

    @PostMapping("/image")
    public R uploadImage(MultipartFile imageFile) {
        if (null != imageFile) {
            String imageUrl = uploadFilesUtil.uploadForImage(imageFile, imageRootPath);
            if (null != imageUrl) {
                return R.ok().data("image_url", imageUrl);
            }
            return R.error().message("上传图片失败");
        }
        return R.error().message("上传图片不能为空");
    }
}
