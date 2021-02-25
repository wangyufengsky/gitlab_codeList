package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import entrys.CommitsBean;
import gitlabApi.ApiPakage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import service.GetCommitsFileDiffList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public JFXTextArea result;
    @FXML
    private AnchorPane context;
    public JFXComboBox comboBranch;
    public ObservableList<String> branchesList;
    public JFXComboBox comboMan;
    public ObservableList<String> authorList;
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField massage;
    public TextField path;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> branches= ApiPakage.getBranchName();
        branchesList = FXCollections.observableArrayList();
        branchesList.addAll(branches);
        comboBranch.setItems(branchesList);
        //comboBranch.getSelectionModel().select(0);
        Map<String,String> authors= ApiPakage.getAuthorName();
        List<String> authorNames=new ArrayList<>();
        authors.forEach((key,value)->{
            authorNames.add(key+"("+value+")");
        });
        authorList=FXCollections.observableArrayList();
        authorList.addAll(authorNames);
        comboMan.setItems(authorList);
        comboBranch.setEditable(false);
    }


    public void getCodeList(ActionEvent actionEvent) {
        Thread t=new Thread(()->{
            try {
                Object branchObject=comboBranch.getValue();
                Object manObject=comboMan.getValue();
                Object startObject=startDate.getValue();
                Object endObject=endDate.getValue();
                String branch="";
                String man="";

                if(null!=branchObject){
                    branch=branchObject.toString();
                }
                if(null!=manObject){
                    man=manObject.toString();
                    man=man.substring(man.indexOf("(")+1,man.indexOf(")"));
                }
                String start="";
                String end="";
                if(null!=startObject){
                    start=startObject.toString().replace("-","");
                }
                if(null!=endObject){
                    end=endObject.toString().replace("-","");
                }
                String massageText=massage.getText();
                String pathText=path.getText();
                GetCommitsFileDiffList getCommitsFileDiffList=new GetCommitsFileDiffList();
                List<CommitsBean> commitsBeans=getCommitsFileDiffList.getCommitsFileDiffList(branch,start,end,pathText,man,massageText);
                result.appendText("\n\n\n");
                commitsBeans.forEach(bean->result.appendText(bean.toString()));
                result.appendText("\n清单生成成功!");
                result.appendText("\n默认生成路径在D:/gitCodeList/gitlab代码变更清单.xlsx");
            }catch (Exception e){
                result.appendText("\n报错了!清单生成失败!错误:" + e);
                e.printStackTrace();
            }
        });
        Platform.runLater(()->{
            result.appendText("\r\n正在生成中!请等待!");
            t.start();
        });

    }
}
