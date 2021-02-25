package entrys;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CommitsBean {
    private String id;
    private String short_id;
    private String title;
    private String author_name;
    private String author_email;
    private String authored_date;
    private String committer_name;
    private String committed_date;
    private String created_at;
    private String message;
    private String parent_ids;
    private String web_url;
    private String last_pipeline;
    private String status;
    private String stats;
    private List<DiffBean> diffBeans;
}
