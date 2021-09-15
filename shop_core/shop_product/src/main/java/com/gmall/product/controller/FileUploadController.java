package com.gmall.product.controller;

import com.gmall.util.common.result.RetVal;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @version v1.0
 * @ClassName FileUploadController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/product/brand")
public class FileUploadController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FileUploadController.class);
    @Value("${fileServer.url}")
    private String fileUrl;

    @RequestMapping("/fileUpload")
    public RetVal<String> fileUpload(MultipartFile file) throws IOException, MyException {
        String configFile = this.getClass().getResource("/tracker.conf").getFile();
        String path = null;

        if (configFile != null) {
            //初始化
            ClientGlobal.init(configFile);
            //创建trackerClient
            TrackerClient trackerClient = new TrackerClient();
            //获取trackerService
            TrackerServer trackerServer = trackerClient.getConnection();
            //创建strongClient1
            StorageClient1 storageClient1 = new StorageClient1();
            path = storageClient1.upload_appender_file1(file.getBytes(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            log.info("url: {}", fileUrl + path);
        }
        return RetVal.ok(fileUrl + path);
    }
}
