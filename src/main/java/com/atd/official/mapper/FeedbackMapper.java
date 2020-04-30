package com.atd.official.mapper;

import com.atd.official.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedbackMapper {

    Integer submitFeedback(Feedback feedback);

    List<Feedback> allFeedback();
}
