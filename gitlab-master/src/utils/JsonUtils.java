package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entrys.CommitsBean;
import entrys.DiffBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {


    public static List<String> parseString4Branches(String entity){
        if(null!=entity){
            Object obj = JSON.parse(entity);
            if(null!=obj){
                if(obj instanceof JSONObject){
                    JSONObject json = JSONObject.parseObject(entity);
                    return  JsonToBranchString(json);
                }else if(obj instanceof JSONArray){
                    JSONArray jsonArray=JSONArray.parseArray(entity);
                    return JsonToBranchString(jsonArray);
                }
            }
        }
        return new ArrayList<>();
    }

    private static List<String> JsonToBranchString(JSONArray jsonArray){
        List<String> list=new ArrayList<>();
        if(null!=jsonArray){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                list.add(object.get("name").toString());
            }
        }
        return list;
    }

    private static List<String> JsonToBranchString(JSONObject json){
        List<String> list=new ArrayList<>();
        if(null!=json){
            list.add(json.get("name").toString());
        }
        return list;
    }

    public static Map<String,String> parseString4Author(String entity){
        if(null!=entity){
            Object obj = JSON.parse(entity);
            if(null!=obj){
                if(obj instanceof JSONObject){
                    JSONObject json = JSONObject.parseObject(entity);
                    return  JsonToAuthorString(json);
                }else if(obj instanceof JSONArray){
                    JSONArray jsonArray=JSONArray.parseArray(entity);
                    return JsonToAuthorString(jsonArray);
                }
            }
        }
        return new HashMap<>();
    }

    private static Map<String,String> JsonToAuthorString(JSONArray jsonArray){
        Map<String,String> map=new HashMap<>();
        if(null!=jsonArray){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                map.put(object.get("name").toString(),object.get("username").toString());
            }
        }
        return map;
    }

    private static Map<String,String> JsonToAuthorString(JSONObject json){
        Map<String,String> map=new HashMap<>();
        if(null!=json){
            map.put(json.get("name").toString(),json.get("username").toString());
        }
        return map;
    }



    public static Map<String, Object> parseString(String entity){
        if(null!=entity){
            Object obj = JSON.parse(entity);
            if(null!=obj){
                if(obj instanceof JSONObject){
                    JSONObject json = JSONObject.parseObject(entity);
                    return  JsonToMap(json);
                }else if(obj instanceof JSONArray){
                    JSONArray jsonArray=JSONArray.parseArray(entity);
                    return JsonToMap(jsonArray);
                }
            }
        }
        return new HashMap<>();
    }

    public static List<CommitsBean> parseJson(String entity){
        if(null!=entity){
            Object obj = JSON.parse(entity);
            if(null!=obj){
                if(obj instanceof JSONObject){
                    JSONObject json = JSONObject.parseObject(entity);
                    return  JsonToBean(json);
                }else if(obj instanceof JSONArray){
                    JSONArray jsonArray=JSONArray.parseArray(entity);
                    return JsonToBean(jsonArray);
                }
            }
        }
        return new ArrayList<>();
    }

    private static Map<String, Object> JsonToMap(JSONArray jsonArray){
        Map<String, Object> map = new HashMap<>();
        if(null!=jsonArray){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                map.put(Integer.toString(i), object);
            }
        }
        return map;
    }

    private static Map<String, Object> JsonToMap(JSONObject json){
        Map<String, Object> map = null;
        if(null!=json){
            map= json;
        }
        return map;
    }

    public static List<DiffBean> diffToBean(String result){
        List<DiffBean> list=new ArrayList<>();
        if(null!=result){
            Object obj = JSON.parse(result);
            if(null!=obj){
                JSONArray jsonArray=JSONArray.parseArray(result);
                if(null!=jsonArray){
                    for(int i=0;i<jsonArray.size();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        beDiffBean(object, list);
                    }
                }
            }
        }
        return list;
    }


    private static List<CommitsBean> JsonToBean(JSONArray jsonArray){
        List<CommitsBean> list=new ArrayList<>();
        if(null!=jsonArray){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                beBean(object, list);
            }
        }
        return list;
    }

    private static List<CommitsBean> JsonToBean(JSONObject json){
        List<CommitsBean> list=new ArrayList<>();
        beBean(json, list);
        return list;
    }

    private static void beBean(JSONObject json, List<CommitsBean> list) {
        list.add(CommitsBean.builder().author_email(json.get("author_email").toString())
            .authored_date(json.get("authored_date").toString())
            .author_name(json.get("author_name").toString())
            .committed_date(json.get("committed_date").toString())
            .committer_name(json.get("committer_name").toString())
            .created_at(json.get("created_at").toString())
            .id(json.get("id").toString())
            .message(json.get("message").toString())
            .parent_ids(json.get("parent_ids").toString())
            .short_id(json.get("short_id").toString())
            .title(json.get("title").toString())
            .web_url(json.get("web_url").toString())
            .build());
    }


    private static void beDiffBean(JSONObject json, List<DiffBean> list) {
        list.add(DiffBean.builder()
                .a_mode(json.get("a_mode").toString())
                .b_mode(json.get("b_mode").toString())
                .deleted_file(json.get("deleted_file").toString())
                .diff(json.get("diff").toString())
                .new_file(json.get("new_file").toString())
                .new_path(json.get("new_path").toString())
                .old_path(json.get("old_path").toString())
                .renamed_file(json.get("renamed_file").toString())
                .build());
    }
}
