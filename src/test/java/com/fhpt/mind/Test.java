package com.fhpt.mind;

import cn.hutool.http.HttpUtil;


import com.alibaba.fastjson.JSONObject;
import com.fhpt.imageqmind.ImageQMindWebApplication;



import com.fhpt.imageqmind.config.MinIOProperties;



import com.fhpt.imageqmind.config.SystemProperties;


import com.fhpt.imageqmind.constant.TrainingStatus;
import com.fhpt.imageqmind.domain.TrainingInfoEntity;


import com.fhpt.imageqmind.job.AsyncTrainingStatusHandler;
import com.fhpt.imageqmind.repository.TagLabelRepository;
import com.fhpt.imageqmind.repository.TrainingInfoRepository;
import com.fhpt.imageqmind.service.DataSetService;

import com.fhpt.imageqmind.service.TrainingInfoService;
import com.fhpt.imageqmind.utils.SpringContextUtil;


import com.obs.services.ObsClient;
import com.obs.services.model.*;
import io.minio.MinioClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import javax.annotation.Resource;



import java.io.*;


import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImageQMindWebApplication.class)
public class Test {

    @Autowired
    private MinIOProperties minIOProperties;

    @Autowired
    private DataSetService dataSetService;

    @Autowired
    private TrainingInfoRepository trainingInfoRepository;
    @Autowired

    private TagLabelRepository tagLabelRepository;
    @Autowired
    private SystemProperties systemProperties;

    @org.junit.Test
    public void test(){
        List<TrainingInfoEntity> trainingInfoEntity = trainingInfoRepository.query(1);
        int i = 0;
    }

    @org.junit.Test
    public void minIO(){

//        try {
//            Class.forName("com.fhpt.imageqmind.utils.MinioUtil");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            MinioClient minioClient = new MinioClient("http://116.62.157.23:9000", "minio",
                    "minio123.!");
//            List<Bucket> bucketList = minioClient.listBuckets();
//            for (Bucket bucket : bucketList) {
//                System.out.println(bucket.creationDate() + ", " + bucket.name());
//            }
            boolean found = minioClient.bucketExists("marty-test");
            if (found) {
                System.out.println("marty-test already exists");
            } else {
                // Create bucket 'marty-test'.
                minioClient.makeBucket("marty-test");
                System.out.println("marty-test is created successfully");
            }
            // Create object 'my-objectname' in 'my-bucketname' with content from the input stream.
            InputStream fileInput = new FileInputStream("C:\\Users\\admin\\Desktop\\test.jpg");
//            minioClient.putObject("marty-test", "test1.jpg", fileInput, fileInput.available(), "image/png");
            // List all incomplete multipart upload of objects in 'marty-test'
//            Iterable<Result<Upload>> myObjects = minioClient.listIncompleteUploads("marty-test");
//            for (Result<Upload> result : myObjects) {
//                Upload upload = result.get();
//                System.out.println(upload.uploadId() + ", " + upload.objectName());
//            }
            //保存文件流
//            InputStream inputStream = minioClient.getObject("marty-test", "test.jpg");
//            OutputStream outputStream = new FileOutputStream("C:\\Users\\admin\\Desktop\\test2.jpg");
//            byte[] bytes = new byte[1024];
//            //每次读取的字符串长度，如果为-1，代表全部读取完毕
//            int len = 0;
//            //使用一个输入流从buffer里把数据读取出来
//            while ((len = inputStream.read(bytes)) != -1) {
//                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//                outputStream.write(bytes, 0, len);
//            }
//            inputStream.close();
//            outputStream.close();
            String url = minioClient.presignedGetObject("marty-test", "test1.jpg", 60 * 60 * 24);
            System.out.println(url);
            //            String url = minioClient.presignedGetObject("my-bucketname", "my-objectname", 60 * 60 * 24);
//            System.out.println(url);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }

    @org.junit.Test
    public void testBucket() throws Exception {
        MinioClient minioClient = new MinioClient("http://10.95.130.144/", "fhpt_minio", "fhptadmin");
        String res = minioClient.presignedGetObject("marty-test", "7e1419a53aebbc350389512e6c05776e.jpg");
        int i = 0;

    }

    @org.junit.Test
    public void test2(){
        String content = "池塘里的一只青蛙被人捉住后放置在温水缸里，舒适的环境让它欣喜不已，不用躲避风雨的侵袭，不必担心阳光的炙烤，它心安理得的沉溺于温暖的水中，梦想早已抛诸脑后，它轻盈的身躯变得臃肿不堪、灵活的双脚变得笨拙，直到有一天，它意识到自己即将成为人类的盘中餐，于是后悔万分。\n" + "\n" + "　　它思念池塘里的泥泞、荷叶和伙伴，向往驰骋于天地间的无拘无束，痛定思痛之后，它开始了跳跃，一次次艰难的尝试之后，终于蹦出了水缸，重新捡起了属于一只青蛙的梦想，尽管前途未卜，但是作为一只跳出温水的青蛙，它已经不同凡响了。\n" + "\n" + "　　关于梦想，从小时候的豪言壮语到现世的知足常乐，人生的种种遭际将孩子们的英雄梦、公主梦无情地击碎，所以我们的梦想变得越来越小，从科学家、文学家到律师、教师，再到为房贷、车贷、生计而奔波的渺小的蚁族。当那个叫梦想的东西从我们的世界里消失，便会无奈、颓废地说一声：“谈梦想太奢侈了。”然后对那些执着地坚守自己梦想的人嗤之以鼻。";
        String tagEnglist = "qingwa";
        int start = 6;
        int end = 7;
        char[] chars = content.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            if (i >= 6 && i <= 7) {
                stringBuilder.append(temp);
                stringBuilder.append(" ");
                stringBuilder.append(tagEnglist);
            }else{
                stringBuilder.append(temp);
                stringBuilder.append(" ");
                stringBuilder.append("O");
            }
            stringBuilder.append("\n");
            if (temp == '。' || temp == '！') {
                stringBuilder.append("\n");
            }
        }
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter("D:\\a.dev");
            fwriter.write(stringBuilder.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void postTest(){
        //发送训练任务请求
        String trainingAddress = systemProperties.getTrainingAddress();
        Map<String, Object> params = new HashMap<>();
        params.put("dataset", "/home/code/docker_ai/taskId5.dev");
        params.put("model_dir", "/home/code/docker_ai/user_models/ner_models/taskId5");
        params.put("train_ratio", 0.8);
        params.put("dev_ratio", 0.1);
        params.put("test_ratio", 0.1);
        params.put("batch_size", 32);
        params.put("lr", 0.001);
        params.put("epochs", 20);
        String res = HttpUtil.post(trainingAddress + "/ner_model_train", params);
        JSONObject json = JSONObject.parseObject(res);
    }

    @Autowired
    private AsyncTrainingStatusHandler asyncTrainingStatusHandler;

    @org.junit.Test
    public void test3() throws Exception {
        String str1 = "哈哈";
        String str2 = "嘻嘻";
        System.out.println(String. format("str1：%d | str2：%d",  str1. hashCode(),str2. hashCode()));
        System.out.println(str1. equals(str2));

    }

    @org.junit.Test
    public void createBucket(){

        String ak = "TF73T4LKIZISJ9RS2RNS";
        String sk = "2PfeoqZWeJlEVR3Bf7ZsmVye10eN9IRVqzCH2Y0e";
        String endPoint = "https://obs.cn-east-2.myhuaweicloud.com";
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName("marty-test1");
        // 设置桶访问权限为私有读写，默认是私有读写
        obsBucket.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
        // 设置桶的存储类型为归档存储
        obsBucket.setBucketStorageClass(StorageClassEnum.STANDARD);
        // 设置桶区域位置
        obsBucket.setLocation("cn-east-2");
        obsClient.createBucket(obsBucket);
        AccessControlList accessControlList = obsClient.getBucketAcl("marty-test");
//        int res1 = obsClient.deleteBucket("marty-test").getStatusCode();
//        int res2 = obsClient.createBucket("marty-test1").getStatusCode();

        //        ObsBucket obsBucket = obsClient.createBucket("marty-test");
//        Date date = obsBucket.getCreationDate();
        int i = 0;
    }
    @org.junit.Test
    public void deleteBucket(){

        String ak = "TF73T4LKIZISJ9RS2RNS";
        String sk = "2PfeoqZWeJlEVR3Bf7ZsmVye10eN9IRVqzCH2Y0e";
        String endPoint = "https://obs.cn-east-2.myhuaweicloud.com";
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        int res = obsClient.deleteBucket("marty-test").getStatusCode();
        int i = 0;
    }

    @org.junit.Test
    public void createSecretImageFile() throws Exception{
        String ak = "TF73T4LKIZISJ9RS2RNS";
        String sk = "2PfeoqZWeJlEVR3Bf7ZsmVye10eN9IRVqzCH2Y0e";
        String endPoint = "https://obs.cn-east-2.myhuaweicloud.com";
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        PutObjectResult putObjectResult = obsClient.putObject("marty-test", "a", new FileInputStream("F:\\a.jpg"));
        System.out.println(putObjectResult.getObjectUrl());
    }

    @org.junit.Test
    public void createPublicImageFile() throws Exception{
        String ak = "TF73T4LKIZISJ9RS2RNS";
        String sk = "2PfeoqZWeJlEVR3Bf7ZsmVye10eN9IRVqzCH2Y0e";
        String endPoint = "https://obs.cn-east-2.myhuaweicloud.com";
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName("marty-test");
        request.setObjectKey("test/b.jpg");
        request.setFile(new File("F:\\a.jpg"));
        // 设置对象访问权限为公共读
        request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
        PutObjectResult putObjectResult = obsClient.putObject(request);
        System.out.println(putObjectResult.getObjectUrl());
    }

    @org.junit.Test
    public void getImageFileUrl(){
        String ak = "TF73T4LKIZISJ9RS2RNS";
        String sk = "2PfeoqZWeJlEVR3Bf7ZsmVye10eN9IRVqzCH2Y0e";
        String endPoint = "https://obs.cn-east-2.myhuaweicloud.com";
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        long expireSeconds = -1;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName("marty-test");
        request.setObjectKey("a.jpg");
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);

        System.out.println("Getting object using temporary signature url:");
        System.out.println("\t" + response.getSignedUrl());
    }

    @org.junit.Test
    public void createDir(){
        String ak = "TF73T4LKIZISJ9RS2RNS";
        String sk = "2PfeoqZWeJlEVR3Bf7ZsmVye10eN9IRVqzCH2Y0e";
        String endPoint = "https://obs.cn-east-2.myhuaweicloud.com";
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        final String keySuffixWithSlash = "test1/";
//        obsClient.putObject("fhpt-project", keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));

        obsClient.deleteObject("fhpt-project", keySuffixWithSlash);

    }

    @org.junit.Test
    public void deleteDir(){
        String ak = "TF73T4LKIZISJ9RS2RNS";
        String sk = "2PfeoqZWeJlEVR3Bf7ZsmVye10eN9IRVqzCH2Y0e";
        String endPoint = "https://obs.cn-east-2.myhuaweicloud.com";
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        try {
            //先清空数据桶，再删除桶，否则失败
            ListVersionsRequest request = new ListVersionsRequest("fhpt-project");
            // 每次批量删除100个对象
            request.setMaxKeys(100);
            request.setPrefix("test1/");
            ListVersionsResult result;
            do {
                // 获取版本集合
                result = obsClient.listVersions(request);
                // 创建删除对象
                DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest("fhpt-project");
                //循环获取到的文件
                for (VersionOrDeleteMarker v : result.getVersions()) {
                    deleteRequest.addKeyAndVersion(v.getKey(), v.getVersionId());
                }
                // 执行删除方法
                obsClient.deleteObjects(deleteRequest);
                request.setKeyMarker(result.getNextKeyMarker());
                request.setVersionIdMarker(result.getNextVersionIdMarker());

            } while (result.isTruncated());
        } catch (Exception e) {
        }
    }

    @org.junit.Test
    public void testDate(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String res = localDateTime.format(dateTimeFormatter);
        System.out.println(res);
        System.out.println(NumberFormat.getCurrencyInstance(Locale.CHINA).format(100));

    }


}
