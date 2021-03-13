package stu.swufe.healthmanager.service;

import org.springframework.web.multipart.MultipartFile;
import stu.swufe.healthmanager.response.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IImageService {
    /**
     * 上传图片
     * @param file
     * @param userId
     * @return
     */
    ResponseResult uploadImage(MultipartFile file, String userId);

    /**
     * 展示图片
     * @param imageId
     * @param response
     * @return
     */
    ResponseResult viewImage(String imageId, HttpServletResponse response) throws IOException;
}
