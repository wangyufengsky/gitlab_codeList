
import entrys.CommitsBean;
import service.GetCommitsFileDiffList;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        GetCommitsFileDiffList getCommitsFileDiffList=new GetCommitsFileDiffList();
        List<CommitsBean> list= getCommitsFileDiffList.getCommitsFileDiffList("feature-soap","20201225","20210107","","","");
    }
}
