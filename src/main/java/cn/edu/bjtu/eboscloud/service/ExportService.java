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
    ExportData findByDevice(String device);
    String del(String device);
    void update(ExportData exportData);
}
