package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.entity.ExportData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by ZhiYuan
 * data:2021/9/1
 */
@Service
public interface ExportService {
    void write(ExportData exportData);
    List<ExportData> findByTopic(String topic);
    List<ExportData> findAll();
    ExportData findByDevice(String device,String topic);
    String del(String device,String topic);
    void update(ExportData exportData);
}
