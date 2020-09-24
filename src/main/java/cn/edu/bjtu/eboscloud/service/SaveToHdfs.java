package cn.edu.bjtu.eboscloud.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SaveToHdfs {

    public void save(String content, String address, String dirPath) throws IOException, URISyntaxException {

        Configuration conf = new Configuration();
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy","NEVER");
        conf.set("dfs.client.use.datanode.hostname", "true");
        conf.setBoolean("dfs.client.block.write.replace-datanode-on-failure.enabled", true);

        String url = "hdfs://" + address + ":9000";

        FileSystem fileSystem = FileSystem.get(new URI(url),conf);
        FSDataOutputStream outputStream;
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String filename = df.format(date);
        String strPath = dirPath + filename;
        Path path = new Path(strPath);
        if (!fileSystem.exists(path)){
            outputStream = fileSystem.create(path);
        }else{
            outputStream = fileSystem.append(path);
        }
        outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        fileSystem.close();
    }
}