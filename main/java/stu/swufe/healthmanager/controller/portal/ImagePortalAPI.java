//package stu.swufe.healthmanager.controller.portal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import stu.swufe.healthmanager.response.ResponseResult;
//import stu.swufe.healthmanager.service.IImageService;
//
//@RestController
//@RequestMapping("/portal/image")
//public class ImagePortalAPI {
//    @Autowired
//    IImageService iImageService;
//
//    @PostMapping
//    public ResponseResult uploadImage(@RequestParam("file")MultipartFile file){
//        return iImageService.uploadImage(file);
//    }
//}
