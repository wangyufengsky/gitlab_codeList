package service;

import entrys.CommitsBean;
import entrys.DiffBean;
import entrys.FileCommitsBean;
import gitlabApi.ApiPakage;
import org.apache.commons.lang.StringUtils;
import utils.ExcelUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetCommitsFileDiffList {





    public List<CommitsBean> getCommitsFileDiffList(String refName,String since,String until,String path,String author,String message){
        List<CommitsBean> commitsBeans=ApiPakage.getCommitsList(refName,since,until,path);
        if(StringUtils.isNotBlank(author)){
            commitsBeans=commitsBeans.parallelStream().filter(s->author.equals(s.getAuthor_name())).collect(Collectors.toList());
        }
        if(StringUtils.isNotBlank(message)){
            commitsBeans=commitsBeans.parallelStream().filter(s->s.getMessage().contains(message)).collect(Collectors.toList());
        }
        commitsBeans.forEach(s->{
            s.setDiffBeans(ApiPakage.getCommitsDiff(s.getShort_id()));
        });
        List<FileCommitsBean> fileBeans=polyDiff(commitsBeans);
        fileBeans.forEach(System.out::println);
        ExcelUtil.writeGitExcel(commitsBeans,fileBeans,since,until);
        return commitsBeans;
    }




    private List<FileCommitsBean> polyDiff(List<CommitsBean> commitsBeans){
        Map<String,FileCommitsBean> map=new HashMap<>();
        commitsBeans.forEach(commitsBean->{
            commitsBean.getDiffBeans().forEach(diffBean ->{
                map.put(diffBean.getNew_path(),FileCommitsBean.builder()
                        .file_name(diffBean.getNew_path())
                        .modes(getModes(diffBean))
                        .build());
            });
        });
        return map.values().parallelStream().collect(Collectors.toList());
    }



    private String getModes(DiffBean diff){
        if("true".equals(diff.getDeleted_file())) return "Deleted File";
        if("true".equals(diff.getNew_file())) return "New File";
        if("true".equals(diff.getRenamed_file())) return "Renamed File";
        return "change File";
    }


}
