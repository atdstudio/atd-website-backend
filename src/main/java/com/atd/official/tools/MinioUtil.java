package com.atd.official.tools;

import com.atd.official.entity.Image;
import com.atd.official.entity.SoftWare;
import com.atd.official.entity.Video;
import com.atd.official.exception.CommonJsonException;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.MinioException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MinioUtil {
    private final String URL = "";
    private final String DOMAIN = "";
    private final int PORT = 9000;
    private final String accessKey = "";
    private final String secretKey = "";
    private final boolean secure = false;

    MinioClient minioClient = new MinioClient(URL, PORT, accessKey, secretKey, secure);

    public MinioUtil() throws InvalidPortException, InvalidEndpointException {
    }

    public boolean delFile(String bucketName, String objectName){
        try {
            minioClient.removeObject(bucketName, objectName);
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException | XmlPullParserException e) {
            System.out.println("Error occurred: " + e);
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
        return true;
    }

    public String downloadURL(String bucketName, String objectName, Integer expires) {
        String url = "";
        try {
            url = minioClient.presignedGetObject(bucketName, objectName, expires);
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException | XmlPullParserException e) {
            System.out.println("Error occurred: " + e);
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
        return url;
    }

    public String uploadURL(String bucketName, String objectName, Integer expires) {

        String url = "";
        try {
            url = minioClient.presignedPutObject(bucketName, objectName, expires);
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException | XmlPullParserException e) {
            System.out.println("Error occurred: " + e);
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
        return url;
    }

    public List<Video> downloadVideoURL(List<Video> result) {
        for (int i = 0; i < result.size(); i++) {
            if ("0".equals(result.get(i).getTemp())) {
                String videoAddress = result.get(i).getVideo_class() + "/" + result.get(i).getLocation();
                String coverAddress = "videoCover" + "/" + result.get(i).getVideo_class() + "/" + result.get(i).getCover_img();
                result.get(i).setLocation(this.downloadURL("video", videoAddress, 6 * 60 * 60 ));
                result.get(i).setCover_img(this.downloadURL("image", coverAddress, 6 * 60 * 60));
            } else {
                String coverAddress = "videoCover" + "/" + result.get(i).getVideo_class() + "/" + result.get(i).getCover_img();
                result.get(i).setLocation(result.get(i).getTemp_link());
                result.get(i).setCover_img(this.downloadURL("image", coverAddress, 60 * 60 * 24));
            }
        }
        return result;
    }

    public List<SoftWare> downloadSoftWareURL(List<SoftWare> result) {
        for (int i = 0; i < result.size(); i++) {
            if ("0".equals(result.get(i).getTemp())) {
                String softwareAddress = result.get(i).getSoftWare_class() + "/" + result.get(i).getLocation();
                String coverAddress = "softwareCover" + "/" + result.get(i).getSoftWare_class() + "/" + result.get(i).getCover_img();
                result.get(i).setLocation(this.downloadURL("software", softwareAddress, 6 * 60 * 60));
                result.get(i).setCover_img(this.downloadURL("image", coverAddress, 6 * 60 * 60));
            } else {
                String coverAddress = "softwareCover" + "/" + result.get(i).getSoftWare_class() + "/" + result.get(i).getCover_img();
                result.get(i).setLocation(result.get(i).getTemp_link());
                result.get(i).setCover_img(this.downloadURL("image", coverAddress, 6 * 60 * 60));
            }
        }
        return result;
    }

    public List<Image> downloadImageURL(List<Image> result) {
        for (int i = 0; i < result.size(); i++) {
            String imageAddress = "index" + "/" + result.get(i).getLocation();
            result.get(i).setLocation(this.downloadURL("image", imageAddress, 60 * 60 * 24));

        }
        return result;
    }



}