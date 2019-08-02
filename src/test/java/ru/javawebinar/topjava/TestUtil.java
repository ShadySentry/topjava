package ru.javawebinar.topjava;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
@JsonIgnoreProperties("excess")
public class TestUtil {
    @JsonIgnoreProperties("excess")
    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }
    @JsonIgnoreProperties("excess")
    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action.andReturn()), clazz);
    }
    @JsonIgnoreProperties("excess")
    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(result), clazz);
    }
    @JsonIgnoreProperties("excess")
    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValues(getContent(result), clazz);
    }
}
