package com.xupt.ttms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xupt.ttms.config.exception.CkException;
import com.xupt.ttms.mapper.VideoMapper;
import com.xupt.ttms.pojo.Video;
import com.xupt.ttms.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author 
 * @description 针对表【video】的数据库操作Service实现
 * @createDate 2023-05-13 17:38:58
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
        implements VideoService {

    @Override
    public List<Video> getVideoByMId(Long mId, Long id) {
        List<Video> videos = getVideo(mId);
        if (id == null) {
            //按照观看次数降序，取前三个
            return videos.stream()
                    .sorted(Comparator.comparing(Video::getWatchNum).reversed())
                    .limit(3)
                    .toList();
        }
        // 使用了ArrayList对List集合进行了复制，这样可以保证在遍历时不会对原集合进行修改
        return moveVideos(new ArrayList<>(videos), id);
    }

    @Override
    public Video watchVideo(Integer id) {
        try {
            Video video = getById(id);
            UpdateWrapper<Video> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("watch_num", video.getWatchNum() + 1).eq("id",id);
            update(updateWrapper);
            video.setWatchNum(video.getWatchNum()+1);
            return video;
        } catch (Exception e) {
            throw new CkException(20001, e.getMessage());
        }
    }

    @Override
    public Video getVideoBymId(Long Id) {
        Video video = getById(Id);
        if (video == null) {
            throw new CkException(20001, "视频不存在");
        }
        return video;
    }

    /**
     * 将给定列表中的某个元素（根据ID匹配）移动到列表的开头位置
     *
     * @param list 要调整的集合
     * @param id   要到首部的id
     * @return 在列表中查找具有给定ID的元素，然后将其与第一个元素交换位置，最后返回移动后的列表
     */
    private List<Video> moveVideos(List<Video> list, Long id) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(list.get(i).getId(), id)) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            Video video = list.remove(index);
            list.add(0, video);
        }
        return list;
    }

    private List<Video> getVideo(Long mId) {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Video::getId, Video::getDes, Video::getiSrc, Video::getLen, Video::getvSrc,Video::getmId, Video::getWatchNum)
                .eq(Video::getmId, mId);
        return list(queryWrapper);
    }
}
