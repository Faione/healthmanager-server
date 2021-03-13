package stu.swufe.healthmanager.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.service.IImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user/image")
public class ImageAPI {
    @Autowired
    IImageService iImageService;


    @PostMapping
    public ResponseResult uploadImage(@RequestParam("file")MultipartFile file,
                                      @RequestParam("user_id") String userId){
        return iImageService.uploadImage(file, userId);
    }

    @GetMapping("/{image_id}")
    public ResponseResult viewImage(@PathVariable("image_id") String imageId, HttpServletResponse response){
        try {
            return iImageService.viewImage(imageId, response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.creatFailed("连接错误");
        }
    }
}
