package com.oursurvey.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.oursurvey.dto.MyResponse;
import com.oursurvey.dto.repo.QuestionDto;
import com.oursurvey.dto.repo.QuestionItemDto;
import com.oursurvey.dto.repo.SectionDto;
import com.oursurvey.dto.repo.SurveyDto;
import com.oursurvey.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final JwtUtil jwtUtil;

    @PostMapping
    public MyResponse create(HttpServletRequest request, @RequestBody String json) {
        MyResponse res = new MyResponse();
        JSONObject object = new JSONObject(json);
        SurveyDto.Create surveyDto = getSurveyFromJson(object);

        return res;
    }

    private SurveyDto.Create getSurveyFromJson(JSONObject object) {
        return SurveyDto.Create.builder()
                .subject(object.getString("subject"))
                .content(object.getString("content"))
                .startDate(object.getString("startDate"))
                .endDate(object.getString("endDate"))
                .minute(object.getInt("minute"))
                .openFl(object.getInt("openFl"))
                .closingComment(object.getString("closingComment"))
                .sectionList(getSectionFromJson(object.getJSONArray("sections")))
                .build();
    }

    private List<SectionDto.Create> getSectionFromJson(JSONArray sectionList) {
        List<SectionDto.Create> res = new ArrayList<>();
        for (int i = 0; i < sectionList.length(); i++) {
            JSONObject section = sectionList.getJSONObject(i);
            res.add(SectionDto.Create.builder()
                    .title(section.getString("title"))
                    .content(section.getString("content"))
                    .nextSection(section.getLong("nextSection"))
                    .questionList(getQuestionDtoList(section.getJSONArray("questions")))
                    .build());
        }
        return res;
    }

    private List<QuestionDto.Create> getQuestionDtoList(JSONArray questionList) {
        List<QuestionDto.Create> questionDtoList = new ArrayList<>();
        for (int j = 0; j < questionList.length(); j++) {
            JSONObject question = questionList.getJSONObject(j);
            questionDtoList.add(QuestionDto.Create.builder()
                    .ask(question.getString("ask"))
                    .explain(question.getString("explain"))
                    .multiFl(question.getInt("multiFl"))
                    .essFl(question.getInt("essFl"))
                    .dupFl(question.getInt("dupFl"))
                    .oder(question.getInt("oder"))
                    .questionItemList(getQuestionItemDtoList(question.getJSONArray("questionItems")))
                    .build());
        }
        return questionDtoList;
    }

    private List<QuestionItemDto.Create> getQuestionItemDtoList(JSONArray questionItemList) {
        List<QuestionItemDto.Create> list = new ArrayList<>();
        for (int k = 0; k < questionItemList.length(); k++) {
            JSONObject questionItem = questionItemList.getJSONObject(k);
            list.add(QuestionItemDto.Create.builder()
                    .content(questionItem.getString("content"))
                    .oder(questionItem.getInt("oder"))
                    .nextSection(questionItem.getLong("nextSection"))
                    .build());
        }
        return list;
    }
}
