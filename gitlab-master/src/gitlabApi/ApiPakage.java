package gitlabApi;

import entrys.CommitsBean;
import entrys.DiffBean;
import org.apache.commons.lang.StringUtils;
import service.interFace.HttpFactory;
import service.interFace.impl.GetFactory;
import service.interFace.impl.GetSender;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiPakage {


    private static final String private_token="U7qWucx5_2J1oKBWY_6j";

    public static List<CommitsBean> getCommitsList(String refName,String since,String until,String path){
        String url = "http://10.129.40.170//api/v4/projects/12453/repository/commits?private_token="+private_token+"&ref_name="+refName+"&all=false";
        if(StringUtils.isNotBlank(since)){
            since+="T00:00:00+08:00";
            url+="&since="+since;
        }
        if(StringUtils.isNotBlank(until)){
            until+="T23:59:59+08:00";
            url+="&until="+until;
        }
        if(StringUtils.isNotBlank(path)){
            url+="&path="+path;
        }
        List<CommitsBean> list=new ArrayList<>();
        int num=1;
        do {
            String url1=url+"&per_page=100&page="+num++;
            List<CommitsBean> list1=JsonUtils.parseJson(GetFactory.builder().url(url1).build().produce().Send());
            if(list1.size()!=0){
                list.addAll(list1);
            }else return list;
        }while (list.size()!=0);
        return list;
    }

    public static CommitsBean getCommits(String ids){
        String url = "http://10.129.40.170//api/v4/projects/12453/repository/commits/"+ids+"?private_token="+private_token;
        return JsonUtils.parseJson(GetFactory.builder().url(url).build().produce().Send()).get(0);
    }


    public static List<DiffBean> getCommitsDiff(String ids){
        String url = "http://10.129.40.170//api/v4/projects/12453/repository/commits/"+ids+"/diff?private_token="+private_token;
        return JsonUtils.diffToBean(GetFactory.builder().url(url).build().produce().Send());
    }


    public static List<String> getBranchName(){
        String url = "http://10.129.40.170//api/v4/projects/12453/repository/branches?private_token="+private_token;
        return JsonUtils.parseString4Branches(GetFactory.builder().url(url).build().produce().Send());
    }


    public static Map<String, String> getAuthorName(){
        String url = "http://10.129.40.170//api/v4/projects/12453/members?private_token="+private_token;
        return JsonUtils.parseString4Author(GetFactory.builder().url(url).build().produce().Send());
    }
}
