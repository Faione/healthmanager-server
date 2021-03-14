package stu.swufe.healthmanager.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stu.swufe.healthmanager.common.ImagType;;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.response.ResponseState;
import stu.swufe.healthmanager.service.IImageService;
import stu.swufe.healthmanager.util.IDWorker;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j
@Service
public class ImageServiceImpl implements IImageService {
    @Value("${healthmanager.image.save-path}")
    private String imagePath;

    @Value("${healthmanager.image.max-size}")
    private long imageMaxSize;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");

    private static final String[] typeList = {"png", "jpg", "gif"};

    @Autowired
    IDWorker idWorker;



    @Override
    public ResponseResult uploadImage(MultipartFile file, String userId) {

        // 判断是否有文件
        if(file == null){
            return  ResponseResult.creatFailed("图片不能为空");
        }

        // 用户验证（防止接口泄露）

        // 判断文件类型（请求头中）
        String contentType = file.getContentType();
        ImagType type = ImagType.fromContentTypeToImageType(contentType);

        if(type == null){
            return ResponseResult.creatFailed("不支持的图片类型");
        }

        // 获取相关数据
        String originalFilename =file.getOriginalFilename();

        log.info("originalFilename: " + originalFilename);

        long size = file.getSize();

        log.info("maxSize == > " + imageMaxSize + "\nrealSize == > " + size);

        // 限制文件大小
        if (size > imageMaxSize){
            return ResponseResult.creatFailed("图片不能超过" +(imageMaxSize>20)+ "MB");
        }


        // 根据规则进行命名
        long nowTime = new Date().getTime();
        String randId = String.valueOf(idWorker.nextId());
        String targetPath = getTargetPath(nowTime, type.getType(), randId);
        log.info("targetPath: " + targetPath);

        // 创建文件/路径
        File targetFile = new File(targetPath);

        // 没有路径则创建
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }

        // 保存文件

        try {
            if(!targetFile.exists()){
                targetFile.createNewFile();
            }

            file.transferTo(targetFile);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.creatFailed("图片上传失败");
        }


        // 记录数据库
        String id = createId(nowTime, type.ordinal(), randId);

        log.info("ImageId: " + id);

//        ImagePojo imagePojo = new ImagePojo();
//
//        imagePojo.setId(id);

        return ResponseResult.createSuccess(ResponseState.SUCCESS, id);
    }

    @Override
    public ResponseResult viewImage(String imageId, HttpServletResponse response) throws IOException {
        // 判断id是否为空
        if(imageId == null){
            return ResponseResult.creatFailed();
        }


        // 将id转化为图片路径
        log.info("viewImage: " + "imageId -> " + imageId);

        String imgPath = idToPath(imageId);

        log.info("viewImage: " + "imgPath -> " + imgPath);

        // 设置response参数
        response.setContentType(String.format("image/%s", imageId.substring(0, 3)));

        // 获得response的outputStream并传输图片
        File imageFile = new File(imgPath);

        OutputStream out = response.getOutputStream();


        InputStream in = new FileInputStream(imageFile);

        // 设置缓冲
        byte[] bf = new byte[1024];
        int len;
        while((len = in.read(bf)) != -1){
            out.write(bf,0, len);
        }
        out.flush();

        if(in != null) in.close();
        if(out != null) out.close();

        // 不再调用 response，而返回空
        // ???? 情窦初开的时候我在这儿想着你
        return null;
    }

    public  String idToPath(String imageId) {

        int typeCode = imageId.charAt(0) - '0';
        long nowTime = Long.valueOf(imageId.substring(1, 14));
        String randID = imageId.substring(14);
        System.out.println("typeCode: " + typeCode);
        ImagType type = ImagType.fromIndexToImageType(typeCode);

        return getTargetPath(nowTime, type.getType(), randID);
    }

    private String createId(long nowTime, int typeCode, String randId) {

        return   String.valueOf(typeCode) + String.valueOf(nowTime) + randId;

    }


    private String getTargetPath(long nowTime, String type, String randId) {
       /*
       path like: dataPath/jpg/1949_10_1/uuid.jpg
       */
        String currentDay = simpleDateFormat.format(nowTime);

        return imagePath +
                File.separator +
                currentDay +
                File.separator +
                type +
                File.separator +
                randId
                + "." + type;

    }


}
