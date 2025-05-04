package cn.bit.oss.util;

import cn.bit.core.constant.OssFilePath;
import cn.bit.core.exception.BizException;
import com.aliyun.oss.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class OssUtil {

    private final OSS ossClient;
    private final String bucketName;
    private final String endPoint;

    @Autowired
    public OssUtil(OSS ossClient,
                   @Value("${spring.cloud.alicloud.oss.bucket.name}") String bucketName,
                   @Value("${spring.cloud.alicloud.oss.endpoint}") String endPoint) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
        this.endPoint = endPoint;
    }
    /**
     * 上传文件到OSS
     *
     * @param file 上传的文件
     * @param folder 目标文件夹
     * @return 文件地址
     */
    public String uploadFile(MultipartFile file, String folder) {
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + fileExtension;
        // 构建完整路径
        String filePath = folder + OssFilePath.SEPARATOR + fileName;
        try {
            // 上传文件流
            ossClient.putObject(bucketName, filePath, file.getInputStream());
            // 生成访问URL
            return filePath;
        } catch (Exception e) {
            throw new BizException("文件上传失败:" + originalFilename, e);
        }
    }


    /**
     * 生成文件访问URL
     *
     * @param filePath 文件路径
     * @return 可访问的URL
     */
    public String generateUrl(String filePath) {
        // 设置URL过期时间为10年
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        URL url = ossClient.generatePresignedUrl(bucketName, filePath, expiration);
        return url.toString().replace("http://" + bucketName + "." + endPoint,
            "https://" + bucketName + "." + endPoint);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public void deleteFile(String filePath) {
        ossClient.deleteObject(bucketName, filePath);
    }
}
