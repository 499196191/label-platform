package com.fhpt.mind;

import com.fhpt.imageqmind.ImageQMindWebApplication;



import com.fhpt.imageqmind.config.MinIOProperties;

import com.fhpt.imageqmind.service.DataSetService;
import com.fhpt.imageqmind.utils.SpringContextUtil;


import io.minio.MinioClient;


import io.minio.Result;
import io.minio.errors.MinioException;

import io.minio.messages.Bucket;

import io.minio.messages.Upload;
import org.junit.runner.RunWith;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import javax.annotation.Resource;



import java.io.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImageQMindWebApplication.class)
public class Test {

    @Autowired
    private MinIOProperties minIOProperties;

    @Autowired
    private DataSetService dataSetService;

    @org.junit.Test
    public void test(){
        Object o = SpringContextUtil.getBean("tagLabelServiceImpl");
        SpringContextUtil.containsBean("tagLabelServiceImpl");
        int i = 0;
    }

    @org.junit.Test
    public void minIO(){

        try {
            Class.forName("com.fhpt.imageqmind.utils.MinioUtil");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            MinioClient minioClient = new MinioClient("https://play.min.io", "Q3AM3UQ867SPQQA43P2F",
                    "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");
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
    public void testAOP(){
        dataSetService.query(1, 10);
        int i = 0;
    }
}
